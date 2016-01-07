package app;

public class Utility {
	public static String simplifyClassName(String name){
		String[] nameArr = name.split("[/]");
		return nameArr[nameArr.length-1];
	}
	
	public static String simplifyType(String name){
		String[] nameArr = name.split("[.]");
		return nameArr[nameArr.length-1];
	}
}
