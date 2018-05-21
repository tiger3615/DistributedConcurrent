package thread.verifyThreadCommunicate;

public class WaitNotify {
	private static volatile long i;

	public synchronized void testWait() throws InterruptedException {
		 this.wait();
			System.out.println("wait: " + (System.currentTimeMillis() - i));
			System.exit(0);
	}

	public synchronized void testSync() throws InterruptedException {
		System.out.println("Sync ");
	}

	public synchronized void testNotify() throws InterruptedException {
			i = System.currentTimeMillis();
			this.notify();
			System.out.println("testNotify");
	}

	public static void main(String[] a) {
		final WaitNotify waitNotify = new WaitNotify();
		// generate 100 thread to wait
		for (int i = 0; i < 10; i++) {
			new WaitThread(waitNotify, "WaitThread: " + i).start();
		}
		// generate 10 thread to normal get object lock
		for (int i = 0; i < 10; i++) {
			new SyncThread(waitNotify, "SyncThread: " + i).start();
		}
		new NotifyThread(waitNotify, "NotifyThread: ").start();
	}
}
