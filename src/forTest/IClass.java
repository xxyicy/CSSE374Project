package forTest;

import java.util.List;
import java.util.Set;


public interface IClass extends ITraverser {
	public void addMethod(IMethod m);
	public void addField(IField f);
	public void addDeclaration(IDeclaration d);
	public IDeclaration getDeclaration();
	public List<IMethod> getMethods();
	public List<IField> getFields();
	public String getName();
	public Set<String> getTags();
	public void addTag(String tag);
	
	
	
	
	public boolean isVisible();
	void setVisible(boolean v);
}
