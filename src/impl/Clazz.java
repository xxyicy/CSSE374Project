package impl;

import java.util.ArrayList;
import java.util.List;

import visitor.api.IVisitor;
import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;
import api.IMethodRelation;

public class Clazz implements IClass {
	private List<IMethod> methods = new ArrayList<IMethod>();
	private List<IField> fields = new ArrayList<IField>();
	private IDeclaration declaration;
	private List<IMethodRelation> methodR = new ArrayList<IMethodRelation>();
	
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
		result += "methods " + this.methods + "\n";
		result += "fields " + this.fields + "\n";
		result += "declaration " + this.declaration + "\n";
		return result;

	}

	@Override
	public String getName() {
		return this.declaration.getName();
	}

	@Override
	public void addMethod1(IMethodRelation m) {
		// TODO Auto-generated method stub
		this.methodR.add(m);
	}

	@Override
	public List<IMethodRelation> getMe() {
		// TODO Auto-generated method stub
		return this.methodR;
	}

}
