package api;

import visitor.api.ITraverser;

public interface IField extends ITraverser {
	public String getName();
	public String getType();
	public String getAccess();
	public boolean isStatic();
	void setStatic(boolean isStatic);
}
