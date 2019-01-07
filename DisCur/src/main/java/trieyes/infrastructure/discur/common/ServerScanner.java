package trieyes.infrastructure.discur.common;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;

import trieyes.infrastructure.discur.core.Conf;
/*
 * scan every server by IP list. find out the small IP as concurrent server
 */
public class ServerScanner {
	private static final Logger logger = LoggerFactory.getLogger(ServerScanner.class);
	/*
	 * concurrent thread server
	 */
	private static volatile String availableIP;

	public static String getAvailableIP() {
		return availableIP;
	}

	public static Thread scannerThread = new Thread() {
		public void run() {
			while (true) {
				// update available server IP by every ms time
				long scanServerIntervalms=1;
				try {
					Conf conf = Conf.getIns();
					scanServerIntervalms = conf.getScanServerIntervalms();
					availableIP = findAvailableIP();
				} catch (Throwable e) {
					logger.error("", e);
				}
				Util.sleep(scanServerIntervalms);
			}
		}
	};
	static {
		try {
			availableIP = findAvailableIP();
			scannerThread.setName("Thread_server_scanner");
			scannerThread.start();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	public static String findAvailableIP() throws Exception {
		Conf conf = Conf.getIns();
		int port = conf.getPort();
		JSONArray ipArr = conf.getIPs();
		for (int i = 0; i < ipArr.size(); i++) {
			String ip = ipArr.getString(i);
			if (ping(ip, port)) {
				return ip;
			}
		}
		return ipArr.getString(0);
	}
	/**
	 * test port is touchable 
	 * @param ip
	 * @param port
	 * @return
	 */
	public static boolean ping(String ip, int port) {
		try {
			Socket socket = new Socket(ip, port);
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
