package forTest;


import java.util.HashSet;
import java.util.Set;




public class Pattern implements IPattern{
	private String name;
//	private HashMap<IClass,String> tagMap;
//	private Set<IRelation> relations;
	private Set<IClass> classes;
	
	
	
	
		
	
	public Pattern(String name){
		this.name = name;
//		this.tagMap = new HashMap<IClass, String>();
//		this.relations = new HashSet<IRelation>();
		this.classes = new HashSet<IClass>();
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
//	@Override
//	public String getTag(IClass c) {
//		
//		return this.tagMap.get(c);
//	}
//	
//	@Override
//	public Set<IRelation> getRelations() {
//		return this.relations;
//	}
	
	@Override
	public Set<IClass> getClasses() {
		return this.classes;
	}

//	@Override
//	public void addRelation(IRelation r) {
//		this.relations.add(r);
//		
//	}

	@Override
	public void addClass(IClass c) {
		this.classes.add(c);
		
	}

//	@Override
//	public void addTag(IClass c, String tag) {
//		this.classes.add(c);
//		this.tagMap.put(c, tag);
//		
//	}
	
	@Override
	public String toString(){
		String result = "";
		result += name + ":\n";
		for(IClass c : this.classes){
			result += " "+c.getName();
//			String tag = this.getTag(c);
//			if(tag!=null){
//				result += ": "+tag;
//			}
			result += "\n";
		}
		
//		for(IRelation r: this.relations){
//			result += "   ";
//			result += r.toString();
//		}
		
		return result;
		
	}
	
}
