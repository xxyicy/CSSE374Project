package api;

import java.util.List;

import visitor.api.ITraverser;

public interface IMethod extends ITraverser {
	public String getName();
	public String getType();
	public String getAccess();
	public List<String> getParamTypes();
	public List<String> getExceptions();
	public String getClassName();
}
