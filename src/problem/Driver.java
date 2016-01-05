package problem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {
	public static void main(String[] args) throws IOException, InterruptedException{
		Path dir = Paths.get("./input_output");
		AppManager m = new AppManager(dir);
		
		m.start();
		
		
		
		
		
		//The second string parameter is the command : thus we can add whatever app we want,
		//we can also target on files of certain type.
	
		
		System.out.println();
		System.out.println("Start adding handle functions to different handlers");
		//add handler to the manager
		m.addActionToCreate(".txt", "open");
		m.addActionToCreate(".temp","notepad");
		m.addActionToDelete(".txt","close");
		m.addActionToDelete(".temp", "close");
		m.addActionToModify(".modify", "open");
		m.addActionToModify(".temp", "notepad");
		
		
		
		System.out.println("Change directory start: (Note that each handler prints a directory change)");
		
		m.changeDirectory(Paths.get("./input_what?????"));
		System.out.println();
		System.out.println("Change directory start again: (Note that each handler prints a directory change)");
		m.changeDirectory(dir);
		System.out.format("Launcher started watching %s ...%nPress the return key to stop ...", dir);

		// Wait for an input
		System.in.read();
		
		
		
		m.stopGracefully();
		m.join();

		System.out.println("Directory watching stopped ...");
		
		
	}
}
