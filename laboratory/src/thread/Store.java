package thread;

public class Store {
	volatile int  i;
	public synchronized void increase() throws InterruptedException {
		if(i==1) {
			this.wait();
		}
		i++;
		this.notify();
		System.out.println("increase: "+i);
	}
	public synchronized void decrease() throws InterruptedException {
		if(i==0) {
			this.wait();
		}
		i--;
		this.notify();
		System.out.println("decrease: "+i);
	}
}
