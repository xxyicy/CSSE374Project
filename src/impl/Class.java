package impl;

import java.util.ArrayList;
import java.util.List;

import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;
import visitor.api.IVisitor;

public class Class implements IClass {
	private List<IMethod> methods = new ArrayList<IMethod>();
	private List<IField> fields = new ArrayList<IField>();
	private IDeclaration declaration;

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
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
		result += "methods " + this.methods + "\n";
		result += "fields " + this.fields + "\n";
		result += "declaration " + this.declaration + "\n";
		return result;

	}

}
