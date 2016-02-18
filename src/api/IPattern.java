package api;


import java.util.Set;

import visitor.api.ITraverser;

public interface IPattern extends ITraverser {
	public String getName();
//	public String getTag(IClass c);
//	public Set<IRelation> getRelations();
	public Set<IClass> getClasses();
	
//	public void addRelation(IRelation r);
	public void addClass(IClass c);
//	public void addTag(IClass c, String tag);
	
	
}
