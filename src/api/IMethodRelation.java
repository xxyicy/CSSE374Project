package api;

import java.util.List;

public interface IMethodRelation {
	public IMethod getFrom();
	public List<IMethod> getTo();
	public void addMethod(IMethod m);
}
