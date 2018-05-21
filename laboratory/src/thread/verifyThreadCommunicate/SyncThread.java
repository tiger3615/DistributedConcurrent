package thread.verifyThreadCommunicate;

public class SyncThread extends Thread {
	private WaitNotify waitNotify;

	public SyncThread(WaitNotify waitNotify, String id) {
		this.waitNotify = waitNotify;
		super.setName(id);
	}

	public void run() {
		try {
			while (true) {
				waitNotify.testSync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
