package pattern.impl;

import pattern.api.IDetector;
import impl.Pattern;
import api.IClass;
import api.IModel;
import api.IPattern;
import api.patternCode;
public class SingletonDetector implements IDetector {

	@Override
	public void detect(IModel m) {
		for(IClass c : m.getClasses()){
			if(isSingleton(c)){
				IPattern p = new Pattern("Singleton");
				p.addClass(c);
				c.addTag("Singleton");
				m.addPattern(p);
			}
		}
	}
	
	
	private boolean isSingleton(IClass c){
		return (patternCode.Singleton & c.getDeclaration().getCode()) == patternCode.Singleton;
	}
	
}
