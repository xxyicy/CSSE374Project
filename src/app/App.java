package app;

import impl.Clazz;
import impl.Method;
import impl.Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import visitor.api.ISDVisitor;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.SDEditOutputStream;
import api.IClass;
import api.IMethod;
import api.IModel;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;

public class App {
	
	public static void main(String[] args) throws Exception {
		
		if (args.length < 2) {
			throw new Exception("Not Enough Parameters");
		}
		
		if (args[0].equals("UML")){
			createUmlDiagram(args[1]);
		}
		else if(args[0].equals("SD")){
			String[] params = new String[2];
			params[0] = args[1];
			params[1] = args[2];
			createSequenceDiagram(params);
		}
		else{
			throw new Exception("Command not found");
		}
		
	
	}
	
	
	
	
	public static void createUmlDiagram(String arg) throws IOException{
		List<Class<?>> classes = ClassFinder.find(arg);		
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}

		
		GraphVizOutputStream v = new GraphVizOutputStream();
		IModel m = new Model();	
		v.Start();
		
		
		for (String clazz : cs){
			ClassReader reader = new ClassReader(clazz);

			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,c,m);
			
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor,c,m);
			
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor,c,m);			
		
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			
			if(!c.getName().contains("$")){
				m.addClass(c);
			}		
		}
		
		m.accept(v);
		
		v.end();
	
		// Tell the Reader to use our (heavily decorated) ClassVisitor to visit the class
		
		PrintWriter writer = new PrintWriter("./output/output.txt");
		writer.print(v.toString());
		writer.close();
		
		System.out.println(v.toString());
		System.out.println(m);
	}
	
	
	
	
	
	
	public static void createSequenceDiagram(String[] args) throws Exception{
		if (args.length < 1) {
			throw new Exception("No given method name");
		}

		System.out.println(args[0]);

		String methodFQS = args[0];

		int depth = args.length == 2 ? Integer.valueOf(args[1]) : 5;

		String[] methodInfo = Utility.parseMethodSignature(methodFQS);
		String methodClassName = methodInfo[0];
		String methodName = methodInfo[1];
		List<String> params = new ArrayList<String>();
		for (int i = 2; i < methodInfo.length; i++) {
			params.add(methodInfo[i].split(" ")[0]);
		}

		System.out.println(params);

		IMethod startMethod = new Method(methodName, "", "", params,
				new ArrayList<String>(), methodClassName);

		List<String> classesRead = new ArrayList<String>();

		Utility.readClassAndMethods(startMethod, depth, classesRead);
		
		ISDVisitor v = new SDEditOutputStream();
		startMethod.accept(v);
		System.out.println(v.toString());
		
		
		PrintWriter writer = new PrintWriter("./output/output.txt");
		writer.print(v.toString());
		writer.close();

//		System.out.println(startMethod.printCallChains(0));

	}

	
	
}
