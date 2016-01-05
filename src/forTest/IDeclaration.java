package forTest;

import java.util.List;

import visitor.api.ITraverser;

public interface IDeclaration extends ITraverser {
	public String getType();
	public String getName();
	public String getSuper();
	public List<String> getInterfaces();
}
