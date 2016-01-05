package problem;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppManager extends Thread implements IAppManager  {
	
	private final WatchService watcher;
	private Path dir;
	private boolean stop;
	private List<Process> processes;
	private CreateHandler createHandler;
	private ModifyHandler modifyHandler;
	private DeleteHandler deleteHandler;
	
	
	
	AppManager(Path dir) throws IOException {
		this.stop = true;
		this.dir = dir;
		this.createHandler = new CreateHandler(dir);
		this.modifyHandler = new ModifyHandler(dir);
		this.deleteHandler = new DeleteHandler(dir);
		this.processes = Collections.synchronizedList(new ArrayList<Process>());
		this.watcher = FileSystems.getDefault().newWatchService();
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);	
	}
	
	
	@Override
	public void stopGracefully() throws IOException {
		this.stop = true;
		File file = new File(dir.toFile() + "/.temp");

		// Let's force the while loop in the run method to come out of the blocking watcher.take() call here
		// You can also create a directory by calling file.mkdir()
		file.createNewFile();
		
	}

	@Override
	public int getApplicationsCount() {
		
		return this.processes.size();
	}

	@Override
	public void startAll() {
		this.stop = false;
		
	}

	@Override
	public void changeDirectory(Path dir) {
		this.dir = dir;
		this.createHandler.changeDirectory(dir);
		this.deleteHandler.changeDirectory(dir);
		this.modifyHandler.changeDirectory(dir);
		System.out.println("Change Directory to "+dir.toString());
	}

	
	
	
	
	@Override
	public boolean isRunning() {
		return !this.stop;
	}


	@Override
	public void run() {
		this.stop = false;
		while(!stop) {
			// Wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			// Context for directory entry event is the file name of entry
			List<WatchEvent<?>> events = key.pollEvents();
			if(!events.isEmpty()) {
				@SuppressWarnings("unchecked")
				WatchEvent<Path> event = (WatchEvent<Path>)events.get(0);
				// Call the handler method
				Path name = event.context();
				String eventType = event.kind().name();
				ProcessBuilder processBuilder = null;
				if(eventType=="ENTRY_CREATE"){
					processBuilder = this.createHandler.handleDirectoryEvent(name);
				}
				else if(eventType == "ENTRY_DELETE"){
					processBuilder = this.deleteHandler.handleDirectoryEvent(name);
				}
				else if(eventType == "ENTRY_MODIFY"){
					processBuilder = this.modifyHandler.handleDirectoryEvent(name);
				}
				else{
					System.out.println("Unknown type of event");
				}
				
				if(processBuilder == null){
					System.out.println("Unable to find handle functions to handle this file");
				}
				else{
				try {
					
					// Start and add the process to the processes list
					Process process = processBuilder.start();
					this.processes.add(process);
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
			}

			// Reset key and remove from set if directory no longer accessible
			if (!key.reset()) {
				break;
			}
		}

		// We gracefully stopped the service now, let's delete the temp file
		this.clearEverything();
		
	}
	
	
	protected void clearEverything() {
		File file = new File(dir.toFile() + "/.temp");
		file.delete();
		synchronized(this.processes){
			for(Process p: this.processes) {
				p.destroy();
			}
		}
		
	}




	@Override
	public Path getDirectory() {
		return this.dir;
	}


	@Override
	public void addActionToCreate(String filetype, String command) {
		this.createHandler.registerType(filetype, command);
		
	}


	@Override
	public void addActionToDelete(String filetype, String command) {
		this.deleteHandler.registerType(filetype, command);
		
		
	}


	@Override
	public void addActionToModify(String filetype, String command) {
		this.modifyHandler.registerType(filetype, command);
		
		
	}


	@Override
	public void removeActionFromCreate(String filetype) {
		this.createHandler.removeType(filetype);
		
	}


	@Override
	public void removeActionFromDelete(String filetype) {
		this.deleteHandler.removeType(filetype);
		
	}


	@Override
	public void removeActionFromModify(String filetype) {
		this.modifyHandler.removeType(filetype);
		
	}


	@Override
	public void removeHander(IDirectoryEventHandler d) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
