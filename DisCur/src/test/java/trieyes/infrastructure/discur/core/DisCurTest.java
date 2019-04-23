package trieyes.infrastructure.discur.core;

import trieyes.infrastructure.discur.common.Util;

public class DisCurTest {
	public static void main(String []a) throws Exception {
		DisCur.init();
		t1.start();
		Util.sleep(100);
		t2.start();
		t1.join();
		
		t2.join();
		System.out.println(counter);
		System.exit(0);
	}
	public static int counter=0;
	public static Thread t1=new Thread() {
		public void run() {
			for(int i=0;i<100;i++) {
				try {
					DisCur.lock("k1");
					if(counter<0) {
						System.out.println("start wait");
						long ct=System.currentTimeMillis();
						DisCur.myWait();
						System.out.println(String.format("sleep %d ms", System.currentTimeMillis()-ct));
					}
					counter--;
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					DisCur.unlock("k1");
				}
			}
		}
	};
	public static Thread t2=new Thread() {
		public void run() {
			for(int i=0;i<100;i++) {
				try {
					DisCur.lock("k1");
					counter++;
					if(counter>0) {
						DisCur.myNotify();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					DisCur.unlock("k1");
				}
			}
		}
	};
}
