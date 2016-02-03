package impl;

import java.util.HashSet;
import java.util.Set;

import visitor.api.IVisitor;
import api.IDeclaration;

public class Declaration implements IDeclaration{
	private String type;
	private String name;
	private Set<String> tags;
	
	
	public Declaration(String type, String name){
		this.type = type;
		this.name = name;
		this.tags = new HashSet<String>();
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "type " + this.type + "  ";
		result += "name " + this.name + "  ";
		return result;
	}
	
	
	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}

	@Override
	public Set<String> getTags() {
		return this.tags;
	}

	@Override
	public void addTag(String tag) {
		this.tags.add(tag);
		
	}	

}
