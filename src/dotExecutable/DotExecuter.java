package dotExecutable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DotExecuter {
	private String inputPath;
	private String outputPath;
	private String dotPath;

	public DotExecuter(String dotPath, String inputPath, String outputPath) {
		this.dotPath = dotPath;
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}

	public void execute() throws IOException, InterruptedException {
		String s = null;
		
	
		
		
		String toExecute = this.dotPath + " -Tpng " + this.inputPath+" -o "+this.outputPath;
		
		System.out.println(toExecute);

		Process p = Runtime.getRuntime().exec(toExecute);

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(
				p.getErrorStream()));

		// read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
		}

		// read any errors from the attempted command
		System.out
				.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}

		p.waitFor();
	}

}
