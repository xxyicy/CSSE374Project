package forTest;

public class Utility {
	public static String simplifyClassName(String name){
		name = name.replaceAll("[.]", "/");
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
	

	
}
