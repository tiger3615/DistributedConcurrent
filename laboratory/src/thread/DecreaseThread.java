package thread;

public class DecreaseThread extends Thread{
	Store store;

	public DecreaseThread(Store store,String id) {
		this.store = store;
		super.setName(id);
	}

	public void run() {
		try {
			while (true) {
				store.decrease();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
