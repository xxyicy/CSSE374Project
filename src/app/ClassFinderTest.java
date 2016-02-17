package app;




import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pattern.api.IDetector;
import pattern.impl.AdapterDetector;
import pattern.impl.CompositeDetector;
import pattern.impl.DecoratorDetector;
import pattern.impl.SingletonDetector;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.IOutputStream;
import visitor.impl.SDEditOutputStream;



public class ClassFinderTest {

	public static void main(String[] args) throws Exception {

	
		Utility.APP_TYPE = Utility.APP_UML;
		List<Class<?>> classes = ClassFinder.find("starbuzz");
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}


		


	}


}
