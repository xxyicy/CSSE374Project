package forTest;


import java.util.Set;

import visitor.api.ITraverser;

public interface IModel extends ITraverser {
	public Set<IClass> getClasses();
	public Set<IRelation> getRelations();
	public void addClass(IClass c);
	public void addRelation(IRelation r);
	
}
