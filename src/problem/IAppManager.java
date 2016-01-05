package problem;

import java.io.IOException;
import java.nio.file.Path;


public interface IAppManager {
	public void stopGracefully() throws IOException;
	public int getApplicationsCount();
	public void startAll();
	public void changeDirectory(Path dir);
	public boolean isRunning();
	public void addActionToCreate(String filetype,String command);
	public void addActionToDelete(String filetype,String command);
	public void addActionToModify(String filetype,String command);
	
	public void removeActionFromCreate(String filetype);
	public void removeActionFromDelete(String filetype);
	public void removeActionFromModify(String filetype);
	
	public void removeHander(IDirectoryEventHandler d);
	public Path getDirectory();
}
