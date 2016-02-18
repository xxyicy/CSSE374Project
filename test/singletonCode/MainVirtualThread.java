package singletonCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainVirtualThread extends Thread {
	private volatile static MainVirtualThread uniqueInstance;
	private List<VirtualThread> threadArray;
	public static int count = 1;
	private boolean finished = false;

	private MainVirtualThread() {
		threadArray = Collections.synchronizedList(new ArrayList<VirtualThread>());
	}

	public static MainVirtualThread getInstance() {
		if (uniqueInstance == null) {
			synchronized (MainVirtualThread.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new MainVirtualThread();
				}
			}
		}
		return uniqueInstance;
	}

	public void addThread(VirtualThread v) {
		synchronized (this.threadArray) {
			this.threadArray.add(v);
		}
	}
	
	public List<VirtualThread> getThreadArray(){
		return threadArray;
	}

	@Override
	public void run() {
		while (MainVirtualThread.count > 0) {
			while (threadArray.size() > 0) {
				while (MainVirtualThread.count < 3 && threadArray.size() > 0) {
					Thread t = threadArray.get(0);
					synchronized (this.threadArray) {
						threadArray.remove(0);
					}
					MainVirtualThread.count++;
					t.start();
					System.out.println("The total amount of thread is " + Thread.activeCount());
				}
			}
			if (!finished) {
				MainVirtualThread.count--;
				finished = true;
			}
		}
		for (int i = 0; i < 5; i++) {
			System.out.println("Hello from " + this.getName());
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Finish all the thread.");
	}

}
