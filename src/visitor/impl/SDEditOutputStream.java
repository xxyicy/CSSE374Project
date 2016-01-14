package visitor.impl;

import java.util.Hashtable;
import java.util.Set;

import api.IMethod;
import api.IMethodRelation;


public class SDEditOutputStream  {
	private StringBuffer declaration;
	private StringBuffer content;
	private String name;
	private int depth;
	private Hashtable<IMethod, IMethodRelation> methodRelations;

	public SDEditOutputStream(int depth, String name, Hashtable<IMethod,IMethodRelation> methodRelations) {
		this.declaration = new StringBuffer();
		this.content = new StringBuffer();
		this.name  = name;
		this.depth = depth;
		this.methodRelations = methodRelations;
	}
	
	public void parse(){
		
	}

}
