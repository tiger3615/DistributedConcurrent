package trieyes.infrastructure.discur.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this is socket server for receiving connection from terminal manager and
 * terminal. So in spring context there are 2 instances. one port is for
 * terminal manager and other for terminal
 * 
 * @author 01373525
 *
 */
public class ThreadSocketServer {
	private ServerSocket server;
	private int port;
	private String name;
	public static long id;
	private static final Logger logger = LoggerFactory.getLogger(ThreadSocketServer.class);

	public ThreadSocketServer(int port, String name) throws Exception {
		this.name = name;
		this.port = port;
		init();
	}

	private void init() throws Exception {
		server = new ServerSocket(this.port);
	}

	public void startListener() {
		Thread thread = new Thread() {
			public void run() {
				while (true) {
					Socket socket = null;
					try {
						socket = server.accept();
						initClient(socket);
					} catch (Throwable e) {
						logger.error("error when initClient", e);
						if (socket != null) {
							try {
								socket.close();
							} catch (IOException e1) {
								logger.error("error when initClient", e1);
							}
						}
					}
				}
			}
		};
		thread.setName(port + " at " + name);
		thread.start();
	}

	private void initClient(final Socket socket) throws Exception {
		String msg = Util.read(socket);
		String msgStr = msg.intern();
		String address = socket.getRemoteSocketAddress().toString();
		Thread thread = new Thread() {
			public void run() {
				try {
					synchronized (msgStr) {
						OutputStream os = socket.getOutputStream();
						PrintWriter pw = new PrintWriter(os);
						BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						// tell remote thread to run
						Util.write(pw, "");
						String signal = null;
						// if read "" means thread over
						// else run command: wait, notify, notifyAll
						while (!(signal = Util.read(br)).equals("")) {
							if (signal.startsWith("wait")) {
								String waitArges[] = signal.split("\\s+");
								if (waitArges.length == 1) {
									msgStr.wait();
								} else {
									long waitTime = Long.parseLong(waitArges[1]);
									msgStr.wait(waitTime);
								}

								// after get lock object. tell remote to run
								Util.write(pw, "");
							} else if ("notify".equals(signal)) {
								msgStr.notify();
							} else if ("notifyAll".equals(signal)) {
								msgStr.notifyAll();
							}
						}
					}
				} catch (Exception e) {
					try {
						Util.write(socket, "");
					} catch (Exception e1) {
						logger.error("Discur", e1);
					}
					logger.error("Discur", e);
				}
			}
		};
		thread.setName(String.format("%s:%s:%d", msgStr, address, id++));
		thread.start();
	}

}
