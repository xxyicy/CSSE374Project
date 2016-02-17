package app;

import pattern.api.IDetector;
import visitor.impl.IOutputStream;
import impl.Clazz;
import impl.Method;


import java.io.IOException;
import java.util.ArrayList;

import java.util.List;


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


public class FrameworkInterface {
		
	public void detectPattern(IDetector d, IModel m) throws Exception{
		d.detect(m);
	}
	
	
	

	
	
	
	public IMethod generateStartMethod(String methodFQS){
		

		String[] methodInfo = Utility.parseMethodSignature(methodFQS);
		String methodClassName = methodInfo[0];
		String methodName = methodInfo[1];
		List<String> params = new ArrayList<String>();
		for (int i = 2; i < methodInfo.length; i++) {
			params.add(methodInfo[i].split(" ")[0]);
		}

		return new Method(methodName, "", "", params, new ArrayList<String>(), methodClassName);
	}
	
	
	public void loadMethodCallRelations(int depth, IMethod start) throws IOException{
		
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
	
	
	public void writeOutput(IOutputStream out, String appType,IMethod start, IModel model){
		if(appType.equals("SD")){
			 out.write(start);
		}
		else{
			out.start();
			out.write(model);
			out.end();
		}
	}
	
	

}
