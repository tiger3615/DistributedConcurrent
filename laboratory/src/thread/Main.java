package thread;

public class Main {

	public static void main(String[] args) {
		Store s=new Store();
		DecreaseThread deThread=new DecreaseThread(s,"De 1");
		IncreaseThread inThread=new IncreaseThread(s,"In 1");
		inThread.start();
		deThread.start();
		deThread=new DecreaseThread(s,"De 2");
		deThread.start();
	}
}
