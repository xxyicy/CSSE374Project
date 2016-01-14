package impl;

import java.util.List;

import visitor.api.IVisitor;
import api.IMethod;


public class Method implements IMethod {
	private String name;
	private String type;
	private String access;
	private List<String> params;
	private List<String> exceptions;
	private String className;
	
	
	
	public Method(String name,String type,String access,List<String> params, List<String> exceptions,String className){
		this.name = name;
		this.type = type;
		this.access = access;
		this.params = params;
		this.exceptions = exceptions;
		this.className = className;
		
	}
	
	
	@Override
	public String toString() {
		String result = "";
		result += "type:" + this.type + "  ";
		result += "name:" + this.name + "  ";
		result += "access:" + this.access + "  "; 
		result += "params:" + this.params + "  ";
		result += "exceps:" + this.exceptions;
		return result;
	}
	
	
	@Override
	public String getName() {
		String s = this.name;
		s = s.replaceAll("<", "");
		s = s.replaceAll(">", "");
		
		return s;
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
	public List<String> getExceptions() {
		return exceptions;
		
	}

	@Override
	public List<String> getParamTypes() {
		return this.params;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}


	@Override
	public String getClassName() {
		
		return this.className;
	}

}
