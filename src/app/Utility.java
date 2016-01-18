package app;

import java.util.HashSet;
import java.util.Set;

import api.IClass;
import api.IModel;
import api.IRelation;

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
		return result;
	}
	
}
