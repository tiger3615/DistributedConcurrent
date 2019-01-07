package trieyes.infrastructure.discur.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import trieyes.infrastructure.discur.common.ServerScanner;
import trieyes.infrastructure.discur.common.ThreadSocketServer;
import trieyes.infrastructure.discur.common.Util;

public class DisCur {
	private static boolean inited = false;
	private static ThreadLocal<BufferedReader> reader = new ThreadLocal<BufferedReader>();
	private static ThreadLocal<PrintWriter> writer = new ThreadLocal<PrintWriter>();
	private static final Logger logger = LoggerFactory.getLogger(DisCur.class);
	/**
	 * This method must run earlier to make sure server ready!
	 * @throws Exception
	 */
	public static void init() throws Exception {
		if (!inited) {
			inited = true;
			startInnerServer();
		}
	}
	/**
	 * 
	 * @param key 
	 *    Any server runs with same key consider same lock
	 * @throws Exception
	 */
	public static void lock(String key){
		try {
			Socket socket = new Socket(ServerScanner.getAvailableIP(), Conf.getIns().getPort());
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			writer.set(pw);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			reader.set(br);
			Util.write(pw, key);
			Util.read(br);
		} catch (Throwable e) {
			logger.error("",e);
		}
		
	}
	
	/**
	 * 
	 * @throws Exception 
	 */
	public static void myWait() throws Exception {
		PrintWriter pw = writer.get();
		Util.write(pw, "wait");
		BufferedReader br = reader.get();
		Util.read(br);
	}
	
	/**
	 * 
	 * @throws Exception 
	 */
	public static void myWait(long waitTime) throws Exception {
		PrintWriter pw = writer.get();
		Util.write(pw, "wait  "+waitTime);
		BufferedReader br = reader.get();
		Util.read(br);
	}
	
	/**
	 * 
	 * @throws Exception 
	 */
	public static void myNotify() throws Exception {
		PrintWriter pw = writer.get();
		Util.write(pw, "notify");
	}
	
	/**
	 * 
	 * @throws Exception 
	 */
	public static void myNotifyAll() throws Exception {
		PrintWriter pw = writer.get();
		Util.write(pw, "notifyAll");
	}
	
	/**
	 * please call in finnally block
	 * @param key
	 */
	public static void unlock(String key) {
		PrintWriter pw = writer.get();
		Util.writeAndClose(pw, "");
	}

	public static void startInnerServer() throws Exception {
		//if current computer IP in server IP list, start the thread server port listener
		if (Conf.getIns().contains(Util.getLocalIP())) {
			ThreadSocketServer mySocketServer = new ThreadSocketServer(Conf.getIns().getPort(),"DisCur server");
			mySocketServer.startListener();
		}
	}

}
