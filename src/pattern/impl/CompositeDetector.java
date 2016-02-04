package pattern.impl;

import impl.Pattern;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import api.IClass;
import api.IField;
import api.IMethod;
import api.IModel;
import api.IPattern;
import api.IRelation;
import pattern.api.IDetector;

public class CompositeDetector implements IDetector {
	


	@Override
	public void detect(IModel m) throws Exception {
		for (IClass c : m.getClasses()) {
			Set<String> supers = this.getSuperClassNames(c, m.getRelations());
			Set<IClass> parentChain = this.getSuperClasses(supers, m);
			parentChain.add(c);
			Map<String, IClass> inters = this.getAllInterfaces(parentChain, m);

			IField f = this.detectChildList(supers, c);

			if (f != null) {
				
				String component = f.getInnerType();
				if(this.checkComponent(component, m)){
					System.out.println("found in super");
					String end = component;
					String start = c.getName();
					Set<String> componentList = new HashSet<String>();
					this.addComponents(start, end, m, componentList);
					
					this.updatePatternInformationWithSuper(c, componentList, m);
				}

			}

			f = this.detectChildList(inters.keySet(), c);
			if (f != null) {
				
				String component = f.getInnerType();
				
				if(this.checkComponent(component, m)){
					IClass superClass = inters.get(component);
					String end = superClass.getName();
					String start = c.getName();
					Set<String> componentList = new HashSet<String>();
					this.addComponents(start, end, m, componentList);
					
					this.updatePatternInformationWithSuper(c, componentList, m);
				}
				
			}

		}

	}
	
	
	private void updatePatternInformationWithSuper(IClass composite, Set<String> components, IModel m ) throws Exception{
		IPattern pattern = new Pattern("Composite");
		
		composite.addTag("Composite");
		pattern.addClass(composite);
		
		for(String component : components){
			IClass c = this.getClassByName(m, component);
			if(c==null){
				throw new Exception("Unexpected class not found while updating components");
			}
			c.addTag("Component");
			pattern.addClass(c);
			for(String sub: this.getAllDescendents(m, component)){
				IClass subClass = this.getClassByName(m, sub);
				if(subClass == null){
					throw new Exception("Unexpected class not found while tracing subclasses");
				}
				if(subClass != composite){
					subClass.addTag("Leaf");
					pattern.addClass(subClass);
				}
			}
		}
		
		m.addPattern(pattern);
	}
	
	
	

	private void addComponents(String start, String end,IModel m, Set<String> list){
		for (IRelation r: m.getRelations()){
			if(r.getFrom().equals(start) && r.getType().equals("extends")){
				list.add(r.getTo());
				if(!r.getFrom().equals(end)){
					this.addComponents(r.getTo(), end, m, list);
				}
			}
		}
		
	}
	

	
	
	
	private boolean checkComponent(String component, IModel model)
			throws Exception {
		System.out.println("checking " + component);
		IClass c = this.getClassByName(model, component);
		if (c == null) {
			throw new Exception("Unexpected class not found exception");
		}
		String className = component.replaceAll("/", ".");
		for (IMethod m : c.getMethods()) {
			
			if (m.getParamTypes().contains(className)) {
				return true;
			}
		}
		return false;

	}

	private Set<String> getAllDescendents(IModel m, String name) {
		Set<String> result = new HashSet<String>();
		this.addSubClasses(m, name, result);
		return result;
	}

	private void addSubClasses(IModel m, String name, Set<String> list) {
		for (IRelation ir : m.getRelations()) {
			if (ir.getType().equals("extends") && ir.getTo().equals(name)) {
				list.add(ir.getFrom());
				addSubClasses(m, ir.getFrom(), list);
			}
		}
	}

	private IField detectChildList(Set<String> supers, IClass c) {

		for (IField f : c.getFields()) {
			String inner = f.getInnerType();

			if (inner != null) {
				if (supers.contains(inner) && this.checkFieldUsedInMethod(f, c)) {
					return f;
				}
			}
		}
		return null;
	}

	private boolean checkFieldUsedInMethod(IField f, IClass c) {
		for (IMethod m : c.getMethods()) {
			String inner = f.getInnerType().replaceAll("/", ".");
			if (m.getParamTypes().contains(inner)) {

				return true;
			}
		}
		return false;
	}

	private Map<String, IClass> getAllInterfaces(Set<IClass> css, IModel m) {
		Map<String, IClass> result = new HashMap<String, IClass>();
		for (IClass c : css) {
			for (String inter : this.getInterfaces(c, m.getRelations())) {
				result.put(inter, c);
			}
		}
		return result;
	}

	private Set<IClass> getSuperClasses(Set<String> cs, IModel m)
			throws Exception {
		Set<IClass> result = new HashSet<IClass>();
		for (String s : cs) {
			IClass c = this.getClassByName(m, s);
			if (c != null) {
				result.add(c);
			} else {
				throw new Exception("Class not found, should not happen");
			}

		}
		return result;

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

	private IClass getClassByName(IModel m, String name) {
		for (IClass c : m.getClasses()) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	private Set<String> getSuperClassNames(IClass c, Set<IRelation> r) {
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
