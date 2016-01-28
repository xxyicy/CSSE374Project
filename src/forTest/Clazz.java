package forTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import visitor.api.IVisitor;
import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;

public class Clazz implements IClass {
	private List<IMethod> methods = new ArrayList<IMethod>();
	private List<IField> fields = new ArrayList<IField>();
	private IDeclaration declaration;
	
	@Override
	public void accept(IVisitor v) {
		v.visit(this);
		this.declaration.accept(v);
		
		if(!this.fields.isEmpty()){
			v.visit("|");
			for(IField f : this.fields){
				f.accept(v);
			}
		}
		
		if(!this.methods.isEmpty()){
			v.visit("|");
			for(IMethod m : this.methods){
				m.accept(v);
			}
		}
		
		v.postVisit(this);
	}

	@Override
	public void addMethod(IMethod m) {
		this.methods.add(m);

	}

	@Override
	public void addField(IField f) {
		this.fields.add(f);

	}

	@Override
	public void addDeclaration(IDeclaration d) {
		this.declaration = d;

	}

	@Override
	public IDeclaration getDeclaration() {
		return this.declaration;
	}

	@Override
	public List<IMethod> getMethods() {
		return this.methods;
	}

	@Override
	public List<IField> getFields() {
		return this.fields;
	}

	@Override
	public String toString() {
		String result = "";
		result += "ClassName: " + this.getName() + "\n";
		result += "ClassCode: ";
		result += "methods " + this.methods + "\n";
		result += "fields " + this.fields + "\n";
		result += "declaration " + this.declaration + "\n";
		result += "tags "+ this.getTags()+"\n";
		return result;

	}

	@Override
	public String getName() {
		return this.declaration.getName();
	}

	public Set<String> getTags() {
		return this.declaration.getTags();
	}

	public void addTag(String tag) {
		this.declaration.addTag(tag);
	}


}
