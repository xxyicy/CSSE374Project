package api;


import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import visitor.api.ITraverser;

public interface IModel extends ITraverser {
	public Set<IClass> getClasses();
	public Set<IRelation> getRelations();
	public void addClass(IClass c);
	public void addRelation(IRelation r);
	boolean contains(IRelation other);
	public Hashtable<IMethod,IMethodRelation> getMethodRelation();
	public void addMethodRelation(IMethod m, IMethodRelation mr);
}
