package app;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IMethod;
import api.IModel;
import api.IRelation;
import asm.SequenceMethodVisitor;

public class Utility {
	public static String simplifyClassName(String name){
		name = name.replaceAll("[.]", "/");
		String[] nameArr = name.split("/");
		return nameArr[nameArr.length-1];
	}
	
	public static String simplifyType(String name){
		String[] nameArr = name.split("[.]");
		return nameArr[nameArr.length-1];
	}
	
	public static boolean isNotBuiltIn(String name){
		if (name.startsWith("java"))
			return false;
		if (name.equals("char"))
			return false;	
		if (name.equals("int"))
			return false;
		if (name.equals("float"))
			return false;
		if (name.equals("boolean"))
			return false;
		return true;
	}
	
	public static Set<IRelation> removeRelationNotInPackage(IModel m) {
		Set<String> names = new HashSet<String>();
		for(IClass c: m.getClasses()){
			String tmp = Utility.simplifyClassName(c.getName());
			names.add(tmp);
		}	
		Set<IRelation> newRelations = new HashSet<IRelation>();
		for (IRelation r : m.getRelations()){
			String from =  Utility.simplifyClassName(r.getFrom());
			String to =  Utility.simplifyClassName(r.getTo());
			if(names.contains(from) && names.contains(to)){
				newRelations.add(r);
			}
		}
		return newRelations;
	}
	
	public static String[] parseMethodSignature(String input){
		int lastDotIndex = input.lastIndexOf(".");
		String className = input.substring(0, lastDotIndex);
		className.replaceAll("/", ".");
		String methodNameAndParams = input.substring(lastDotIndex + 1);
		int firstparIndex = methodNameAndParams.lastIndexOf("(");
		String method = methodNameAndParams.substring(0, firstparIndex);
		String params = methodNameAndParams.substring(firstparIndex + 1, methodNameAndParams.length() - 1);
		String[] paramArr;
		if (params.contains(",")) {
			paramArr = params.split(",");
		}else if (params.length() == 0){
			paramArr = new String[0];
		}else{
			paramArr = new String[1];
			paramArr[0] = params;
		}
		String[] result = new String[2+paramArr.length];
		result[0] = className;
		result[1] = method;
		for(int i = 0;i<paramArr.length;i++){
			result[i+2] = paramArr[i];
		}
		
		for(int i = 2; i< result.length;i++){
			if(result[i].contains("<") && result[i].contains(">")){
				int firstIndex = result[i].indexOf("<");
				result[i] = result[i].substring(0,firstIndex);
			}
		}
		return result;
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

	
}
