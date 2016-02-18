package adapterCode;

import java.util.ArrayList;
import java.util.Enumeration;


public class App {
	public static void main(String[] args) throws Exception {
		// We want to use ArrayList instead of Vector here, i.e.,
		
		ArrayList<String> arr = new ArrayList<String>();
		for(int i = 1; i <= 3; ++i) {
			arr.add("Tick Tick " + i);
		}
		
		Enumeration<String> etion = new EnumerationAdapter<String>(arr.iterator());
		LinearTransformer<String> lT = new LinearTransformer<String>(etion);
		lT.transform(System.out);
	}
}
