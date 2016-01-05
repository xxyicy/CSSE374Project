package problem;

import java.nio.file.Path;


public interface IDirectoryEventHandler {
	
	public ProcessBuilder handleDirectoryEvent(Path event);
	public void changeDirectory(Path dir);
	public void registerType(String type,String command);
	public void removeType(String type);
	public Path getDirectory();
}
