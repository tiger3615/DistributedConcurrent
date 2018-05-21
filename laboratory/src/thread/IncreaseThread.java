package thread;

public class IncreaseThread extends Thread {
	Store store;

	public IncreaseThread(Store store,String id) {
		this.store = store;
		super.setName(id);
	}

	public void run() {
		try {
			while (true) {
				store.increase();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
