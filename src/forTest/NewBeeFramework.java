package forTest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;


public class NewBeeFramework {
	private IModel model;
	private IMethod start;
	private String appType;
	private List<String> cs;
	private List<String> additionalClasses;
	private Set<IDetector> detectors;
	private IOutputStream out;

	public NewBeeFramework(String appType,List<String> cs,List<String> additionalClasses, IOutputStream out) {
		this.model = new Model();
		this.appType = appType;
		this.cs = cs;
		this.additionalClasses = additionalClasses;
		this.detectors = new HashSet<IDetector>();
		this.out = out;
		this.start = null;	
	}

	
	public void addDetector(IDetector d){
		this.detectors.add(d);
	}
	
	
	public void processAll() throws Exception{
		this.loadClass();
		this.detectPattern();
		this.writeOutput();
	}
	
	
	
	
	public void detectPattern() throws Exception{
		for(IDetector d : this.detectors){
			d.detect(model);
		}
	}
	
	public void setStartMethod(IMethod m){
		this.start = m;
	}
	
	
	
	
	
	
	public void loadClass() throws IOException{
		this.loadClassRecur();
		this.loadClassNonRecur();
		if(this.start != null){
			this.loadMethodCallRelations();
		}
	}
	
	
	private void loadClassNonRecur() throws IOException {
		if(this.additionalClasses == null){
			return;
		}
		for (String clazz : this.additionalClasses) {
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
	
	
	public void writeOutput(){
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
