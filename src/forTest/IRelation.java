package forTest;

import visitor.api.ITraverser;

public interface IRelation extends ITraverser {
	public String getFrom();
	public String getTo();
	public String getType();
}
