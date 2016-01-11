package api;

import visitor.api.ITraverser;

public interface IDeclaration extends ITraverser {
	public String getType();
	public String getName();
}
