package singletonCode;

<<<<<<< HEAD

=======
>>>>>>> 95b4217b1da29b4b4466e5d25ce35c94b592c081

public class Client {

	public static void main(String[] args) throws InterruptedException {
		MainVirtualThread m = MainVirtualThread.getInstance();

		VirtualThread v1 = new VirtualThread();
		VirtualThread v2 = new VirtualThread();
		VirtualThread v3 = new VirtualThread();
		VirtualThread v4 = new VirtualThread();
		VirtualThread v5 = new VirtualThread();
		VirtualThread v6 = new VirtualThread();
		m.addThread(v1);
		m.addThread(v2);
		m.addThread(v3);
		m.addThread(v4);
		m.addThread(v5);
		m.addThread(v6);

		m.start();
	}

}
