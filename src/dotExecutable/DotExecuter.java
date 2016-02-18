package dotExecutable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import observer.api.Notifier;
import observer.api.Observer;

public class DotExecuter implements Notifier {
	private String inputPath;
	private String outputPath;
	private String dotPath;
	private List<Observer> observers;
	private Process p;
	
	
	public DotExecuter(String dotPath, String inputPath, String outputPath) {
		this.observers = new ArrayList<Observer>();
		this.dotPath = dotPath;
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}

	
	public void execute() throws IOException, InterruptedException {
		

		String toExecute = this.dotPath + " -Tpng " + this.inputPath + " -o "
				+ this.outputPath;

	

		p = Runtime.getRuntime().exec(toExecute);

//		BufferedReader stdInput = new BufferedReader(new InputStreamReader(
//				p.getInputStream()));
//
//		BufferedReader stdError = new BufferedReader(new InputStreamReader(
//				p.getErrorStream()));
//
//		// read the output from the command
//		System.out.println("Here is the standard output of the command:\n");
//		while ((s = stdInput.readLine()) != null) {
//			System.out.println(s);
//		}
//
//		// read any errors from the attempted command
//		System.out
//				.println("Here is the standard error of the command (if any):\n");
//		while ((s = stdError.readLine()) != null) {
//			System.out.println(s);
//		}
		
		p.waitFor();
		
		
		this.notifyObservers(null);

	}

	public void stop(){
		if( this.p!=null){
			this.p.destroyForcibly();
		}
	}
	
	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);

	}

	@Override
	public void notifyObservers(Object data) {
		for (Observer o : this.observers) {
			o.update(data);
		}

	}

}
