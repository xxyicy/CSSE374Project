package impl;

import java.util.ArrayList;
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
		return getLast(this.name);
	}

	@Override
	public String getSuper() {
		return this.getLast(this.superClass);
	}

	@Override
	public List<String> getInterfaces() {
		List<String> tmp = new ArrayList<String>();
		for(String s : this.interfaces){
			tmp.add(this.getLast(s));
		}
		return tmp;	
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
	
	private String getLast(String s){
		String[] tmp = s.split("/");
		return tmp[tmp.length-1];
	}

}
