package problem;


import java.nio.file.Path;
import java.util.HashMap;


public class ModifyHandler implements IDirectoryEventHandler {
	
	private Path file;
	private HashMap<String,String> map = new HashMap<String,String>();
	
	public ModifyHandler(Path file){
		this.file = file;
	
		System.out.println("initialize handler of type modify");
	}
	
	public void registerType(String type, String command){
		System.out.println("modifyHandler: Registering "+type+" type files with command "+command);
		this.map.put(type, command);
	}
	
	@Override
	public ProcessBuilder handleDirectoryEvent(Path event) {
		
		
		ProcessBuilder processBuilder = null;
		String command = null;
		String arg = null;
		Path child = this.file.resolve(event);
		
		
		String fileName = child.toString();
		System.out.println("Processing " + fileName + "...");
		
		command = this.map.get(fileName.split(".")[1]);
		if(command != null){
			arg = fileName;
		}
		else{
			return null;
		}
		
		
		
		System.out.println("Handle create event using command "+command+" on type "+ fileName.split(".")[1]);
		// Run the application if support is available
		
		
		processBuilder = new ProcessBuilder(command, arg);

		return processBuilder;		

	}

	
	
	@Override
	public void changeDirectory(Path dir) {
		this.file = dir;
		System.out.println("modify Handler Change Directory to "+dir.toString());
		
	}


	@Override
	public Path getDirectory() {
		return this.file;
	}

	@Override
	public void removeType(String type) {
		this.map.remove(type);
		System.out.println("Remove handle function from ModifyHandler for type "+type);
		
	}
	
}
