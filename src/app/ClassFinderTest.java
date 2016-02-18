package app;





import java.util.ArrayList;

import java.util.List;

import Framework.ClassFinder;
import Framework.Utility;




public class ClassFinderTest {

	public static void main(String[] args) throws Exception {

	
		Utility.APP_TYPE = Utility.APP_UML;
		List<Class<?>> classes = ClassFinder.find("/Users/mot/Desktop/repositories/CSSE374Project/src");
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}

		System.out.println(cs);
		
 

	}


}
