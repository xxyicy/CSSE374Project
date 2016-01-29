package pattern.impl;

import pattern.api.IDetector;
import impl.Pattern;
import api.IClass;
import api.IField;
import api.IMethod;
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
//		return (patternCode.Singleton & c.getDeclaration().getCode()) == patternCode.Singleton;
		String className = c.getName().replaceAll("/", ".");
		boolean staticField = false;
		for (IField f: c.getFields()){
			if (f.isStatic() && f.getAccess().equals("-") && className.equals(f.getType())){
				staticField = true;
			}
		}
		boolean privateConstructor = true;
		boolean staticMethod = false;
		for (IMethod m: c.getMethods()){
			if (m.getName().equals("<init>") && !m.getAccess().equals("-")){
				privateConstructor = false;
			}
			if (m.isStatic() && m.getReturnType().equals(className) &&m.getAccess().equals("+")){
				staticMethod = true;
			}
		}	
		return staticField && privateConstructor && staticMethod;
	}
	
}
