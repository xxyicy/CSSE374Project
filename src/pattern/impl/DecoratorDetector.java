package pattern.impl;

import impl.Pattern;

import java.util.HashSet;
import java.util.Set;

import pattern.api.IDetector;
import api.IClass;
import api.IField;
import api.IMethod;
import api.IModel;
import api.IPattern;
import api.IRelation;

public class DecoratorDetector implements IDetector {
	private int threshold = 1;
	
	public DecoratorDetector(int threshold){
		this.threshold = threshold;
	}
	
	@Override
	public void detect(IModel m) throws Exception {

		for (IClass c : m.getClasses()) {
			Set<String> paramsInConst = this.getParamInConst(c);
			IField f = this.composeSuper(c, m);
			if (f != null && paramsInConst.contains(f.getType())) {
				this.detectPatternWithField(f, c, m);
			} else {
				IField d = this.composeInterface(c, m);
				if (d != null
						&& paramsInConst.contains(d.getType().replaceAll("[.]",
								"/"))) {
					this.detectPatternWithField(d, c, m);
				}
			}
		}
	}

	private Set<String> getParamInConst(IClass c) {
		Set<String> result = new HashSet<String>();
		for (IMethod m : c.getMethods()) {
			if (m.getName().equals("init")) {
				for (String s : m.getParamTypes()) {
					result.add(s);
				}
			}
		}
		return result;
	}

	private IClass getClassByName(IModel m, String name) {
		for (IClass c : m.getClasses()) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	private void detectPatternWithField(IField f, IClass c, IModel m)
			throws Exception {
		if (c.getDeclaration().getType().equals("abstract")) {
			String fieldType = f.getType();
			fieldType = fieldType.replaceAll("[.]", "/");
			this.updateModelWithPattern(c, m, fieldType);
		} else {
			String fieldType = f.getType();
			fieldType = fieldType.replaceAll("[.]", "/");
			int methodCount  = 0;
			for (IMethod s : c.getMethods()) {
				
				for (IMethod called : s.getCalls()) {
					String calledClass = called.getClassName();
					if (s.getName().equals(called.getName())
							&& fieldType.equals(calledClass)
							&& !calledClass.equals("java/lang/Object")) {
						
						methodCount ++;
						break;
					}
				}
			}
			System.out.println("methodCount is : " + methodCount);
			if(methodCount >= this.threshold){
				this.updateModelWithPattern(c, m, fieldType);
			}
			
			
		}
	}

	private void updateModelWithPattern(IClass c, IModel m, String calledClass)
			throws Exception {
		System.out.println("found" + c.getName() + " decorates " + calledClass);
		IClass component = this.getClassByName(m, calledClass);

		if (component == null) {
			return;
		}
		
		IPattern p = new Pattern("Decorator");
		
		component.addTag("component");

		
		p.addClass(component);
		
		
		this.addTagAndUpdatePattern(p, c, m);
		
	
		for (IRelation i : m.getRelations()) {
			if (i.getFrom().equals(c.getName())
					&& i.getTo().replaceAll("[.]", "/").equals(calledClass)
					&& i.getType().equals("association")) {
				i.setDes("decorates");
			}
		}

		m.addPattern(p);
	}

	
	private void addTagAndUpdatePattern(IPattern p,IClass c, IModel m) throws Exception{
		p.addClass(c);
		c.addTag("decorator");
		for (IRelation i: m.getRelations()){
			if (i.getTo().equals(c.getName()) && i.getType().equals("extends")) {
				IClass from = this.getClassByName(m, i.getFrom());
				if (from == null) {
					throw new Exception("This situation should not happen");
				}
				this.addTagAndUpdatePattern(p, from, m);
			}
		}
	}
	
	
	
	
	private IField composeSuper(IClass c, IModel m) {
		Set<IRelation> rs = m.getRelations();
		Set<String> supers = this.getSuperClasses(c, rs);
		for (IField f : c.getFields()) {
			String target = f.getType().replaceAll("[.]", "/");
			if (supers.contains(target)) {
				// System.out.println(c.getName()+" composes(by extending) of type :"+f.getType());
				return f;
			}
		}
		return null;
	}

	private IField composeInterface(IClass c, IModel m) {
		Set<IRelation> rs = m.getRelations();
		Set<String> supers = this.getInterfaces(c, rs);
		for (IField f : c.getFields()) {
			String target = f.getType().replaceAll("[.]", "/");
			if (supers.contains(target)) {
				// System.out.println(c.getName()+" composes(by implementing) of type :"+f.getType());
				return f;
			}
		}
		return null;
	}

	private Set<String> getInterfaces(IClass c, Set<IRelation> rs) {
		Set<String> result = new HashSet<String>();
		String name = c.getName();
		for (IRelation r : rs) {
			if (r.getFrom().equals(name) && r.getType().equals("implements")) {
				result.add(r.getTo());
			}
		}
		return result;
	}

	private Set<String> getSuperClasses(IClass c, Set<IRelation> r) {
		Set<String> result = new HashSet<String>();
		String name = c.getName();
		while (true) {
			String tmp = this.getSuper(name, r);
			if (tmp != null) {
				result.add(tmp);
				name = tmp;
			} else {
				break;
			}
		}
		return result;

	}

	private String getSuper(String c, Set<IRelation> rs) {
		for (IRelation r : rs) {
			if (r.getFrom().equals(c) && r.getType().equals("extends")) {
				return r.getTo();
			}
		}
		return null;
	}
}
