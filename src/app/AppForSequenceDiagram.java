package app;

import impl.Method;
import api.IMethod;
import asm.SequenceMethodVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;


public class AppForSequenceDiagram {
	public static void main(String[] args) throws Exception {

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

		
		
		
		
		IMethod startMethod = new Method(methodName,"","",params,new ArrayList<String>(),methodClassName);
		
		List<String> classesRead = new ArrayList<String>();
		
		
		
		readClassAndMethods(startMethod,depth,classesRead);
		
		System.out.println(startMethod.printCallChains(0));
		
	
		
	}
	
	
	public static void readClassAndMethods(IMethod current,int curDepth,List<String> classesRead) throws IOException{
		if(curDepth < 1){
			return;
		}
		//add the class to read list
		if(!classesRead.contains(current.getClassName())){
			classesRead.add(current.getClassName());
			ClassReader reader = new ClassReader(current.getClassName());
			ClassVisitor sequenceVisitor = new SequenceMethodVisitor(Opcodes.ASM5, current,current.getClassName());
			reader.accept(sequenceVisitor,ClassReader.EXPAND_FRAMES);
			
			//Recursive call to include all methods called within the range of depth
			for (IMethod m : current.getCalls()) {
				readClassAndMethods(m,curDepth-1,classesRead);
			}
		}
		
		
	}
}
