package api;

import java.util.List;

import visitor.api.ITraverser;

public interface IClass extends ITraverser {
	public void addMethod(IMethod m);
	public void addField(IField f);
	public void addDeclaration(IDeclaration d);
	public void addUses(String u);
	public void addAssociation(String a);
	
	public IDeclaration getDeclaration();
	public List<IMethod> getMethods();
	public List<IField> getFields();
	public String getName();
}
