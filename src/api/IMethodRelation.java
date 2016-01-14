package api;

import java.util.List;

import visitor.api.ITraverser;

public interface IMethodRelation extends ITraverser{
	public IMethod getFrom();
	public List<IMethod> getTo();
	public void addMethod(IMethod m);
}
