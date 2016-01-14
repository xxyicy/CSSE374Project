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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Method other = (Method) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} 
		if(params.size() != other.getParamTypes().size()){
			return false;
		}
		for(int i=0;i<params.size();i++){
			if(!params.get(i).equals(other.params.get(i))){
				return false;
			}
		}
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	

}
