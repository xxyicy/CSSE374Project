package api;

import visitor.api.ITraverser;

public interface IRelation extends ITraverser {
	public String getFrom();
	public String getTo();
	public String getType();
	public String getDes();
	public void setDes(String des);
	
	
	
	public boolean isVisible();
	void setVisible(boolean v);
	
}
