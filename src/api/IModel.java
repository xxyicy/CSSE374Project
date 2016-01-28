package api;

import java.util.Set;

import visitor.api.ITraverser;

public interface IModel extends ITraverser {
	public Set<IClass> getClasses();
	public Set<IRelation> getRelations();
	public void addClass(IClass c);
	public void addRelation(IRelation r);
	boolean contains(IRelation other);
	void addPattern(IPattern pattern);
	Set<IPattern> getPatterns();
	public void setRelation(Set<IRelation> r);
//	public void detectAllPatterns();
}
