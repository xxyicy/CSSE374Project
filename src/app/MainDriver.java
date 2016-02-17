package app;

import java.io.FileNotFoundException;
import java.io.IOException;


public class MainDriver {
	
	private TMXXreader reader;
	
	private String currentTask;
	
	public MainDriver(String path) throws FileNotFoundException{
		this.reader = new TMXXreader(path);
		
	}

	
	
	public void readFile() throws IOException{
		this.reader.readFile();
	}
	
	
	
	
	
	
	public String getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}
	
	
	
	
	
}
