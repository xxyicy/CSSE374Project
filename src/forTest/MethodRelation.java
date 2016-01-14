package forTest;

import java.util.ArrayList;
import java.util.List;

import api.IMethod;
import api.IMethodRelation;
import visitor.api.IVisitor;

public class MethodRelation implements IMethodRelation {
	private IMethod from;
	private List<IMethod> to = new ArrayList<IMethod>();
	
	
	public MethodRelation(IMethod from){
		this.from = from;
		this.to = new ArrayList<IMethod>();
	}
	
	public IMethod getFrom(){
		return this.from;
	}
	
	public List<IMethod> getTo(){
		return this.to;
	}

	@Override
	public String toString(){
		String result = "";
		result += "from: "+from.getClassName() + " "+from.getName() +"\n" + "to: ";
		for (String s: from.getParamTypes()){
			result +=s+",";
		}
		for (IMethod s: to){
			result += s.getClassName()+"  "+s.getName()+",";
		}
		result += "\n";
		return result;		
	}
	
	@Override
	public void addMethod(IMethod m) {
		this.to.add(m);
		
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
	
	
	
}
