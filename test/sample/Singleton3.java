package sample;

public class Singleton3 {
private static Singleton3 uniqueInstance;
	
	private Singleton3() {
		uniqueInstance = new Singleton3();
	}
	
	public static Singleton3 getInstance() {
		return uniqueInstance;
	}
}
