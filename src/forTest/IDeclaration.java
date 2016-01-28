package forTest;

import java.util.Set;

import visitor.api.ITraverser;

public interface IDeclaration extends ITraverser {
	public String getType();
	public String getName();
//	boolean isSingleton();
	void orWithCode(int code);
	void andWithCode(int code);
	public int getCode();
	public Set<String> getTags();
	public void addTag(String tag);
	
}
