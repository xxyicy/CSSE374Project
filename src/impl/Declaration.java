package impl;

import visitor.api.IVisitor;
import api.IDeclaration;

public class Declaration implements IDeclaration{
	private String type;
	private String name;
	private int patternCode;
	
	
	
	public Declaration(String type, String name){
		this.type = type;
		this.name = name;
		this.patternCode = 0b0000;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "type " + this.type + "  ";
		result += "name " + this.name + "  ";
		result += "code " + this.patternCode + "  ";
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
	public boolean isSingleton(){
		
		return (this.patternCode & api.patternCode.Singleton) == api.patternCode.Singleton;
	}

	
	

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
	
	
	@Override
	public void orWithCode(int code){
		this.patternCode = this.patternCode | code;
	}
	

	@Override
	public void andWithCode(int code) {
		this.patternCode = this.patternCode & code;
		
	}
	

	
	

	
	

}
