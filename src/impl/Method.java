package impl;

import java.util.ArrayList;
import java.util.List;
import app.Utility;
import visitor.api.IVisitor;
import api.IMethod;



public class Method implements IMethod {
	
	private String name;
	private String type;
	private String access;
	private List<String> params;
	private List<String> exceptions;
	private String className;
	private List<IMethod> calls;
	private IMethod parent;
	
	
	
	public Method(String name,String type,String access,List<String> params, List<String> exceptions,String className){
		this.name = name;
		this.type = type;
		this.access = access;
		this.params = params;
		this.exceptions = exceptions;
		this.className = className;
		this.calls = new ArrayList<IMethod>();
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
		for (int i=0;i<params.size();i++){
			if (!params.get(i).equals(other.params)){
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


	@Override
	public List<IMethod> getCalls() {
		return this.calls;
	}




	@Override
	public void addCall(IMethod call) {
		// TODO Auto-generated method stub
		this.calls.add(call);
	}





	@Override
	public void setClassName(String c) {
		// TODO Auto-generated method stub
		this.className = c;
	}


	@Override
	public void setReturnType(String c) {
		this.type = c;
	}


	@Override
	public boolean compareMethod(IMethod m) {
		if(this.className.equals(m.getClassName()) && this.name.equals(m.getName()) && this.params.size() ==
				m.getParamTypes().size()){
			for (int i =0; i< this.params.size(); i++){
				String thisParam = Utility.simplifyClassName(this.params.get(i));
				String mParam = Utility.simplifyClassName(m.getParamTypes().get(i));
				if(!thisParam.equals(mParam)){
					return false;
				}
			}
			return true;
		}
		return false;
	}


	@Override
	public String printCallChains(int depth) {
		String result = "";
		for(int i =0;i<depth;i++){
			result += "  ";
		}
		if(this.parent != null){
			result += this.parent.getName()+": ";
		}
		result += "->";
		result += this.className+this.name+"\n";
		for(IMethod m : this.calls){
			result += m.printCallChains(depth +1);
		}
		return result;
	}


	@Override
	public void setParent(IMethod parent) {
		this.parent = parent;
		
	}


	@Override
	public IMethod getParent() {
		return this.parent;
	}

}
