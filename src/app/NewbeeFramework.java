package app;

import pattern.api.IDetector;
import visitor.impl.IOutputStream;
import impl.Clazz;
import impl.Method;
import impl.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IMethod;
import api.IModel;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import asm.SequenceMethodVisitor;

public class NewbeeFramework {
	private IModel model;
	private IMethod start;
	private String appType;
	private List<String> cs;
	private Set<IDetector> detectors;
	private IOutputStream out;

	public NewbeeFramework(String appType,List<String> cs, IOutputStream out) {
		this.model = new Model();
		this.appType = appType;
		this.cs = cs;
		this.detectors = new HashSet<IDetector>();
		this.out = out;
		this.start = null;
		
	}

	public void addDetector(IDetector d){
		this.detectors.add(d);
	}
	
	
	public void process() throws Exception{
		this.loadClass();
		this.detectPattern();
		this.writeOutput();
	}
	
	
	
	
	private void detectPattern() throws Exception{
		for(IDetector d : this.detectors){
			d.detect(model);
		}
	}
	
	
	
	
	
	
	private void loadClass() throws IOException{
		if(this.appType.equals("UMLWP")){
			this.loadClassRecur();
		}else if(this.appType.equals("UML")){
			this.loadClassNonRecur();
		}
		else{
			this.loadMethodCallRelations();
		}
	}
	
	
	private void loadClassNonRecur() throws IOException {
		for (String clazz : cs) {
			ClassReader reader = new ClassReader(clazz);

			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
					c, model);

			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor, c, model);

			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor, c, model);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$")) {
				model.addClass(c);
			}
		}	
	}

	private void loadClassRecur() throws IOException {
		List<String> classRead = new ArrayList<>();
		
		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
					c, model, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
					decVisitor, c, model);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
					fieldVisitor, c, model);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			clazz = clazz.replaceAll("/", ".");
			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				model.addClass(c);
				classRead.add(clazz);
			}
		}
		
		model.setRelation(Utility.removeRelationNotInPackage(model));
		
		
	}
	
	private void loadMethodCallRelations() throws IOException{
		String methodFQS = cs.get(0);
		
		int depth = cs.get(1) != null ? Integer.valueOf(cs.get(1)) : 5;

		String[] methodInfo = Utility.parseMethodSignature(methodFQS);
		String methodClassName = methodInfo[0];
		String methodName = methodInfo[1];
		List<String> params = new ArrayList<String>();
		for (int i = 2; i < methodInfo.length; i++) {
			params.add(methodInfo[i].split(" ")[0]);
		}

		this.start = new Method(methodName, "", "", params, new ArrayList<String>(), methodClassName);

		List<String> classesRead = new ArrayList<String>();

		readClassAndMethods(start, depth, classesRead);
	}
	
	
	
	public static void readClassAndMethods(IMethod current, int curDepth,
			List<String> classesRead) throws IOException {
		if (curDepth < 1) {
			return;
		}
		// add the class to read list

		ClassReader reader = new ClassReader(current.getClassName());
		ClassVisitor sequenceVisitor = new SequenceMethodVisitor(Opcodes.ASM5,
				current, current.getClassName());
		
		reader.accept(sequenceVisitor, ClassReader.EXPAND_FRAMES);

		// Recursive call to include all methods called within the range of
		// depth
		for (IMethod m : current.getCalls()) {
			readClassAndMethods(m, curDepth - 1, classesRead);
		}
	}
	
	
	private void writeOutput(){
		if(this.appType.equals("SD")){
			this.out.write(start);
		}
		else{
			System.out.println(model);
			this.out.start();
			this.out.write(model);
			this.out.end();
		}
	}
	
	

}
