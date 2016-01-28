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

	@Override
	public void detect(IModel m) throws Exception {

		for (IClass c : m.getClasses()) {
			IField f = this.composeSuper(c, m);
			if (f != null) {
				this.detectPatternWithField(f, c, m);
			} else {
				IField d = this.composeInterface(c, m);
				if (d != null) {
					this.detectPatternWithField(d, c, m);
				}
			}
		}
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
			for (IMethod s : c.getMethods()) {
				boolean found = false;
				for (IMethod called : s.getCalls()) {
					String calledClass = called.getClassName();
					String fieldType = f.getType();
					fieldType = fieldType.replaceAll("[.]", "/");

					if (s.getName().equals(called.getName())
							&& fieldType.equals(calledClass)
							&& !calledClass.equals("java/lang/Object")) {
						//
						this.updateModelWithPattern(c, m, calledClass);
						found = true;
						break;

					}
				}
				if (found) {
					break;
				}
			}
		}
	}

	private void updateModelWithPattern(IClass c, IModel m, String calledClass)
			throws Exception {
		System.out.println("found" + c.getName() + " decorates " + calledClass);
		IClass component = this.getClassByName(m, calledClass);

		if(component == null){
			return;
		}
		IPattern p = new Pattern("Decorator");
		c.addTag("decorator");
		component.addTag("component");

		p.addClass(c);
		p.addClass(component);
		// System.out.println(c.getName());
		// System.out.println(calledClass);
		for (IRelation i : m.getRelations()) {
			if (i.getTo().equals(c.getName()) && i.getType().equals("extends")) {
				IClass from = this.getClassByName(m, i.getFrom());
				if (from == null) {
					throw new Exception("This situation should not happen");
				}
				from.addTag("decorator");
				p.addClass(from);
			}

			if (i.getFrom().equals(c.getName())
					&& i.getTo().replaceAll("[.]", "/").equals(calledClass)
					&& i.getType().equals("association")) {
				i.setDes("decorates");
			}
		}

		m.addPattern(p);
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

	private Set<String> getInterfaces(IClass c, Set<IRelation> r) {
		Set<String> result = new HashSet<String>();
		String name = c.getName();
		while (true) {
			String tmp = this.getInterface(name, r);
			if (tmp != null) {
				result.add(tmp);
				name = tmp;
			} else {
				break;
			}
		}
		return result;
	}

	private String getInterface(String c, Set<IRelation> rs) {
		for (IRelation r : rs) {
			if (r.getFrom().equals(c) && r.getType().equals("implements")) {
				return r.getTo();
			}
		}
		return null;
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
