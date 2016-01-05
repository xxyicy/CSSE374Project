package visitor.impl;

import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;
import visitor.api.IVisitor;

public class Visitor implements IVisitor {
	private StringBuffer b;
	
	
	public Visitor(){
		this.b = new StringBuffer();
	}
	
	@Override
	public String toString(){
		return this.b.toString();
	}
	
	public void Start(){
		this.appendln("digraph G {");
		this.appendln("fontname = \"Avenir Book\"");
		this.appendln("fontsize = 10");
		this.appendln("");
		this.appendln("node [");
		this.appendln("fontname = \"Avenir Book\"");
		this.appendln("fontsize = 10");
		this.appendln("shape = \"record\"");
		this.appendln("]");
		this.appendln("");
		this.appendln("edge [");
		this.appendln("fontname = \"Avenir Book\"");
		this.appendln("fontsize = 10");
		this.appendln("]");
	}
	
	public void end(){
		this.appendln("}");
		
	}
	
	public void appendln(String s){
		this.b.append(s);
		this.b.append("\n");
	}
	
	public void append(String s){
		this.b.append(s);
	}
	
	@Override
	public void visit(IClass c) {
		this.appendln(c.getName()+" [");
		this.appendln("shape=\"record\"");
		this.append("label = \"{");
		
	}

	@Override
	public void visit(IMethod m) {
		this.append(m.getAccess()+" "+m.getName()+"(");
		
		if(!m.getParamTypes().isEmpty()){
			int count = 0;
			
			for (String s : m.getParamTypes()){
				String tmp = "arg"+count;
				count ++;
				this.append(tmp+":"+this.getLast(s)+",");
			}
			this.b.deleteCharAt(this.b.length()-1);
		}
		
		this.append(")"+" : "+this.getLast(m.getType()));
		if(!m.getExceptions().isEmpty()){
			this.append(" throws ");
			for(String s : m.getExceptions()){
				this.append(s+",");
			}
			this.b.deleteCharAt(this.b.length()-1);
		}
		this.append("\\l");
	}

	
	private String getLast(String s){
		String[] tmp = s.split("[.]");
		return tmp[tmp.length-1];
	}
	
	@Override
	public void visit(IDeclaration d) {
		this.append("\\<\\<"+ d.getType() + "\\>\\>"+"\\l"+d.getName());
	}

	@Override
	public void visit(IField f) {
		this.append(f.getAccess()+" "+f.getName()+" : "+getLast(f.getType()) + "\\l" );
	}
	
	

	@Override
	public void postVisit(IClass c) {
		this.appendln("}\"");
		this.appendln("];");
		String superC = c.getDeclaration().getSuper();
		this.appendln(c.getName()+" -> " + superC + "[arrowhead=\"onormal\"]");
		for(String i : c.getDeclaration().getInterfaces()){
			this.appendln(c.getName() + " -> " + i + "[arrowhead=\"onormal\",style=\"dashed\"]");
		}
	}

	
	@Override
	public void visit(String s) {
		this.append(s);
	}
	
	

}
