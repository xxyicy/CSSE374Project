package forTest;




import java.io.FileOutputStream;
import java.util.ArrayList;
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

	}

	public static void createUmlWithPattern(String arg) throws Exception {
		Utility.APP_TYPE = Utility.APP_UMLWP;
		List<Class<?>> classes = ClassFinder.find(arg);
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}
		
		IOutputStream graphOut = new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));
		NewbeeFramework nf;
		nf = new NewbeeFramework("UMLWP", cs,graphOut);

		
		//Adding detectors to the framework
		IDetector d = new SingletonDetector();
		nf.addDetector(d);
		IDetector decorator = new DecoratorDetector();
		nf.addDetector(decorator);
		IDetector adapter = new AdapterDetector();
		nf.addDetector(adapter);
		
		IDetector composite = new CompositeDetector();
		nf.addDetector(composite);
		nf.process();

	}

	public static void createUmlDiagram(String arg) throws Exception {
		Utility.APP_TYPE = Utility.APP_UML;
		List<Class<?>> classes = ClassFinder.find(arg);
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}


		IOutputStream graphOut = new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));
		IModel m = new Model();

		NewbeeFramework nf = new NewbeeFramework("UML",cs,graphOut);
		nf.process();


	}

	public static void createSequenceDiagram(String[] args) throws Exception {
		Utility.APP_TYPE = Utility.APP_SD;
		if (args.length < 1) {
			throw new Exception("No given method name");
		}
		
		List<String> cs = Arrays.asList(args);
		
		IOutputStream sdEditOut = new SDEditOutputStream(new FileOutputStream("./output/output.txt"));
		NewbeeFramework nf = new NewbeeFramework("SD",cs,sdEditOut);
		
		
		nf.process();
		
		
		System.out.println(sdEditOut.toString());


		

	}

}
