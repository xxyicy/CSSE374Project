package app;



import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pattern.api.IDetector;
import pattern.impl.AdapterDetector;
import pattern.impl.CompositeDetector;
import pattern.impl.DecoratorDetector;
import pattern.impl.SingletonDetector;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.IOutputStream;
import visitor.impl.SDEditOutputStream;



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

		LandingPage p = new LandingPage();
		p.setDefaultLookAndFeelDecorated(true);
	}

	public static void createUmlWithPattern(String arg) throws Exception {
		Utility.APP_TYPE = Utility.APP_UMLWP;
		List<Class<?>> classes = ClassFinder.find(arg);
		List<String> cs = new ArrayList<>();
		
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}

		
		
		List<String> addi = new ArrayList<>();
		
//		addi.add("javax.swing.JPanel");
//		addi.add("javax.swing.JLabel");
		

		IOutputStream graphOut = new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));
		NewBeeFramework nf;
		nf = new NewBeeFramework("UMLWP", cs,addi,graphOut);

		
		//Adding detectors to the framework
		IDetector d = new SingletonDetector(0);
		nf.addDetector(d);
		IDetector decorator = new DecoratorDetector(1);
		nf.addDetector(decorator);
		IDetector adapter = new AdapterDetector(1);
		nf.addDetector(adapter);
		
		IDetector composite = new CompositeDetector();
		nf.addDetector(composite);
		nf.processAll();

	}

	public static void createUmlDiagram(String arg) throws Exception {
		Utility.APP_TYPE = Utility.APP_UML;
		List<Class<?>> classes = ClassFinder.find(arg);
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}


		IOutputStream graphOut = new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));
		
		NewBeeFramework nf = new NewBeeFramework("UML", cs,null, graphOut);
		
		nf.processAll();


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
