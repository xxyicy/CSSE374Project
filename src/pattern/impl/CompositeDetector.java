package pattern.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import api.IClass;
import api.IField;
import api.IModel;
import api.IRelation;
import pattern.api.IDetector;

public class CompositeDetector implements IDetector {

//	private class FieldString{
//		private IField f;
//		private String s;
//		public FieldString(IField f, String s){
//			this.f = f;
//			this.s = s;
//		}
//		public IField getF() {
//			return f;
//		}
//
//		public String getS() {
//			return s;
//		}	
//	}
	
	@Override
	public void detect(IModel m) throws Exception {
		for (IClass c: m.getClasses()){
			Set<String> supers = this.getSuperClassNames(c, m.getRelations());
			Set<IClass> parentChain = this.getSuperClasses(supers, m);
			parentChain.add(c);
			Map<String,IClass> inters = this.getAllInterfaces(parentChain, m);
			
			IField f = this.detectChildListFromSuper(supers, c);
			if( f!=null ){
				
			}
			else{
				
			}
			
			
		}
		
	}

	
	

	
	
	private IField detectChildListFromSuper(Set<String> supers, IClass c){
		for(IField f:c.getFields()){
			if (supers.contains(f.getInnerType().replaceAll("[.]", "/"))){
				return f;
			}
		}
		return null;
	}
	
	
	
	private Map<String,IClass> getAllInterfaces(Set<IClass> css,IModel m){
		Map<String,IClass> result = new HashMap<String,IClass>();
		for(IClass c : css){
			for (String inter: this.getInterfaces(c, m.getRelations())){
				result.put(inter,c);
			}
		}
		return result;
	}
	
	
	
	private Set<IClass> getSuperClasses(Set<String> cs,IModel m) throws Exception{
		Set<IClass> result = new HashSet<IClass>();
		for(String s : cs){
			IClass c = this.getClassByName(m, s);
			if(c!=null){
				result.add(c);
			}
			else{
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
