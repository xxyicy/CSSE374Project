package impl;

import java.util.List;

import api.IDeclaration;
import visitor.api.IVisitor;

public class Declaration implements IDeclaration{
	private String type;
	private String name;
	private String superClass;
	private List<String> interfaces;
	
	
	public Declaration(String type, String name, String superClass, List<String> interfaces){
		this.type = type;
		this.name = name;
		this.superClass = superClass;
		this.interfaces = interfaces;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "type " + this.type + "  ";
		result += "name " + this.name + "  ";
		result += "superClass " + this.superClass + "  ";
		result += "interfaces: " + this.interfaces;
		return result;
	}
	
	
	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSuper() {
		return this.superClass;
	}

	@Override
	public List<String> getInterfaces() {
		return this.interfaces;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}

}