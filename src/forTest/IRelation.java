package forTest;

import visitor.api.ITraverser;

public interface IRelation extends ITraverser {
	public String getFrom();
	public String getTo();
	public String getType();
	public String getDes();
	public void setDes(String des);
	
}