package pattern.api;

import pattern.impl.IDetector;
import api.IClass;
import api.IModel;
public class AdapterDetector implements IDetector {

	@Override
	public void detect(IModel m) {
		for(IClass c : m.getClasses()){
			
		}
	}
	

	
}
