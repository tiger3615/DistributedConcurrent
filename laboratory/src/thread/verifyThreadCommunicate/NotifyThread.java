package thread.verifyThreadCommunicate;

public class NotifyThread extends Thread{
	private WaitNotify waitNotify;
	public NotifyThread(WaitNotify waitNotify,String id) {
		this.waitNotify = waitNotify;
		super.setName(id);
	}

	public void run() {
		try {
			Thread.sleep(2);
			waitNotify.testNotify();
			//System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
