package forTest;

import visitor.api.IVisitor;
import api.IField;

public class Field implements IField {
	private String name;
	private String type;
	private String access;
	
	public Field(String name,String type,String access){
		this.name = name;
		this.type = type;
		this.access = access;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "type:" + this.type + " ";
		result += "name:" + this.name + " ";
		result += "accss:" + this.access; 
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

}
