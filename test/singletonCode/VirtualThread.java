package singletonCode;

public class VirtualThread extends Thread {

	@Override
	public synchronized void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("Hello from " + this.getName());
			try {
				sleep(300);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		if (MainVirtualThread.count > 1) {
			MainVirtualThread.count--;
		}
		
	}

}
