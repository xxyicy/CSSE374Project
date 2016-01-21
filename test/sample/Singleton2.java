package sample;

public class Singleton2 {
	private volatile static Singleton2 uniqueInstance;
	
	private Singleton2() {
		
	}
	
	public static Singleton2 getInstance() {
		if (uniqueInstance == null) {
			synchronized (Singleton2.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Singleton2();
				}
			}
			
		}
		return uniqueInstance;
	}
}
