package api;

import java.util.List;

import visitor.api.ITraverser;

public interface IMethod extends ITraverser {
	public String getName();
	public String getReturnType();
	public String getAccess();
	public List<String> getParamTypes();
	public List<String> getExceptions();
	public String getClassName();
	public List<IMethod> getCalls();

	public void addCall(IMethod call);

	public void setClassName(String c);
	public void setReturnType(String c);
	public boolean compareMethod(IMethod m);
	public String printCallChains(int depth);
	
}
