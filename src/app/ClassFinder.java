package app;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * from http://stackoverflow.com/questions/15519626/how-to-get-all-classes-names-in-a-package
 */


public class ClassFinder {

    private static final char DOT = '.';

    private static final char SLASH = '/';

    private static final String CLASS_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static List<Class<?>> find(String scannedPackage) {
    	String str="";
        String scannedPath = scannedPackage.replace(DOT, SLASH);
       
        if(scannedPath.contains("/")){
        	 int lIndex =  scannedPath.lastIndexOf("/");
        	 str += scannedPath.substring(lIndex+1, scannedPath.length());
        }else{
        	str+= scannedPath;
        }
        
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(str);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, str, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage) {
    	String str="";
        String scannedPath = scannedPackage.replace(DOT, SLASH);
       
        if(scannedPath.contains("/")){
        	 int lIndex =  scannedPath.lastIndexOf("/");
        	 str += scannedPath.substring(lIndex+1, scannedPath.length());
        }else{
        	str+= scannedPath;
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = str + DOT + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            int endIndex = resource.length() - CLASS_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
		return classes;
	}

}