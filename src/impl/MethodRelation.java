package impl;

import java.util.ArrayList;
import java.util.List;

import api.IMethod;
import api.IMethodRelation;

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
	public void addMethod(IMethod m) {
		this.to.add(m);
		
	}
	
	
	
}
