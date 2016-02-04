package problem.client;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import problem.lib.LinearTransformer;

public class App {
	public static void main(String[] args) throws Exception {
		// We want to use ArrayList instead of Vector here, i.e.,
		 ArrayList<String> vect = new ArrayList<String>();
		 Iterator<String> itr = null;
		//Vector<String> vect = new Vector<String>();
		for(int i = 1; i <= 3; ++i) {
			vect.add("Tick Tick " + i);
		
			
		}

		Iterator<String> iterator = vect.iterator();
		Adapter adt = new Adapter(iterator);
		LinearTransformer<String> lT = new LinearTransformer<String>(adt);
		lT.transform(System.out);
	}
}
