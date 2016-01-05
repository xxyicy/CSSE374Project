package impl;

import java.util.ArrayList;
import java.util.List;

import api.IMethod;
import visitor.api.IVisitor;


public class Method implements IMethod {
	private String name;
	private String type;
	private String access;
	private List<String> params;
	private List<String> exceptions;
	
	public Method(String name,String type,String access,List<String> params, List<String> exceptions){
		this.name = name;
		this.type = type;
		this.access = access;
		this.params = params;
		this.exceptions = exceptions;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "type:" + this.type + "  ";
		result += "name:" + this.name + "  ";
		result += "accss:" + this.access + "  "; 
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
		
		List<String> tmp = new ArrayList<String>();
		for(String s : this.exceptions){
			tmp.add(this.getLast(s));
		}
		return tmp;	
		
	}

	@Override
	public List<String> getParamTypes() {
		return this.params;
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
