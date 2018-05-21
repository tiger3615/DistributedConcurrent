package thread.verifyThreadCommunicate;

public class WaitThread extends Thread{
	private WaitNotify waitNotify;
	public WaitThread(WaitNotify waitNotify,String id) {
		this.waitNotify = waitNotify;
		super.setName(id);
	}

	public void run() {
		try {
			while (true) {
				waitNotify.testWait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
