package app;

import java.util.HashSet;
import java.util.Set;

import api.IClass;
import api.IModel;
import api.IRelation;

public class Utility {
	public static String simplifyClassName(String name){
		name = name.replaceAll("[.]", "[/]");
		String[] nameArr = name.split("[/]");
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
}
