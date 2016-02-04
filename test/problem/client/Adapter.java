package problem.client;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class Adapter implements Enumeration<String> {
	Iterator<String> itr;
	public Adapter(Iterator<String> itr){
		this.itr = itr;
	}
	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		if(itr.hasNext()){
			return true;
		}
		return false;
	}

	@Override
	public String nextElement() {
		// TODO Auto-generated method stub
		return itr.next();
	}

}
