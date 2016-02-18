package forTest;



import java.io.FileOutputStream;

import java.util.Arrays;
import java.util.List;




public class App {

	public static void main(String[] args) throws Exception {

		
		if (args.length < 2) {
			throw new Exception("Not Enough Parameters");
		}
		
		if (args[0].equals("UML")){				
			createUmlDiagram(args[1]);
		} else if (args[0].equals("UMLWP")) {
			createUmlWithPattern(args[1]);
		} else if (args[0].equals("SD")) {
			String[] params = new String[2];
			params[0] = args[1];
			if(args.length>=3){
				params[1] = args[2];
			}
			
			createSequenceDiagram(params);
		} else {
			throw new Exception("Command not found");
		}


		
		
//		ResultFrame p2 = new ResultFrame(null);

	}

	public static void createUmlWithPattern(String arg) throws Exception {
		Utility.APP_TYPE = Utility.APP_UMLWP;
		LandingPage p = new LandingPage();
		
	}

	public static void createUmlDiagram(String arg) throws Exception {
		Utility.APP_TYPE = Utility.APP_UML;
		LandingPage p = new LandingPage();


	}

	public static void createSequenceDiagram(String[] args) throws Exception {
		Utility.APP_TYPE = Utility.APP_SD;
		if (args.length < 1) {
			throw new Exception("No given method name");
		}
		
		List<String> cs = Arrays.asList(args);
		
		IOutputStream sdEditOut = new SDEditOutputStream(new FileOutputStream("./output/output.txt"));
		NewBeeFramework nf = new NewBeeFramework("SD",cs,null,sdEditOut);
		
		
		nf.processAll();
		
		
		System.out.println(sdEditOut.toString());


		

	}

}
