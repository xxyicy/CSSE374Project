package phase.api;

import observer.api.Notifier;

public interface IPhase extends Notifier {
	
	public void execute() throws Exception;
}
