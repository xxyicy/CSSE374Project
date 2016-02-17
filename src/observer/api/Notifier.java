package observer.api;

public interface Notifier {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers(Object data);
	
}
