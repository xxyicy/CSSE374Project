package impl;

import visitor.api.IVisitor;
import api.IField;

public class Field implements IField {
	private boolean isStatic;
	private String name;
	private String type;
	private String access;
	private String innerType;
	private String className;
	
	public Field(String name,String type,String access,String className){
		this.name = name;
		this.type = type;
		this.access = access;
		this.className = className;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "type:" + this.type + " ";
		result += "name:" + this.name + " ";
		result += "accss:" + this.access; 
		if(this.innerType != null){
			result += " "+"innerType: "+this.innerType;
		}
		return result;
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getAccess() {
		return this.access;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}

	@Override
	public boolean isStatic() {
		
		return this.isStatic;
	}

	@Override
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	@Override
	public String getInnerType() {
		return this.innerType;
	}

	@Override
	public void setInnerType(String compositeType) {
		this.innerType = compositeType;
	}

	@Override
	public String getClassName() {
		
		return this.className;
	}

}
