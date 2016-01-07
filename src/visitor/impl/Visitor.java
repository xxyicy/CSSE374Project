package visitor.impl;

import visitor.api.IVisitor;
import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;
import app.Utility;

public class Visitor implements IVisitor {
	private StringBuffer b;

	public Visitor() {
		this.b = new StringBuffer();
	}

	@Override
	public String toString() {
		return this.b.toString();
	}

	public void Start() {
		this.appendln("digraph G {");
		this.appendln("fontname = \"Avenir Book\"");
		this.appendln("fontsize = 10");
		
		this.appendln("node [");
		this.appendln("fontname = \"Avenir Book\"");
		this.appendln("fontsize = 10");
		this.appendln("shape = \"record\"");
		this.appendln("]");
		
		this.appendln("edge [");
		this.appendln("fontname = \"Avenir Book\"");
		this.appendln("fontsize = 10");
		this.appendln("]");
	}

	public void end() {
		this.appendln("}");

	}

	public void appendln(String s) {
		this.b.append(s);
		this.b.append("\n");
	}

	public void append(String s) {
		this.b.append(s);
	}

	@Override
	public void visit(IClass c) {
		this.appendln(Utility.simplifyClassName(c.getName()) + " [");
		this.appendln("shape=\"record\",");
		this.append("label = \"{");

	}

	@Override
	public void visit(IMethod m) {
		this.append(m.getAccess() + " " + m.getName() + "(");

		if (!m.getParamTypes().isEmpty()) {
			int count = 0;

			for (String s : m.getParamTypes()) {
				String tmp = "arg" + count;
				count++;
				this.append(tmp + ":" + Utility.simplifyType(s) + ",");
			}
			this.b.deleteCharAt(this.b.length() - 1);
		}

		this.append(")" + " : " + Utility.simplifyType(m.getType()));
		if (!m.getExceptions().isEmpty()) {
			this.append(" throws ");
			for (String s : m.getExceptions()) {
				this.append(Utility.simplifyClassName(s) + ",");
			}
			this.b.deleteCharAt(this.b.length() - 1);
		}
		this.append("\\l");
	}

	@Override
	public void visit(IDeclaration d) {
		if (d.getType() == "class") {
			this.append(Utility.simplifyClassName(d.getName()));
		}else{
			this.append("\\<\\<" + d.getType() + "\\>\\>" + "\\n" + Utility.simplifyClassName(d.getName()));
		}
	}

	@Override
	public void visit(IField f) {
		this.append(f.getAccess() + " " + f.getName() + " : " + Utility.simplifyType(f.getType()) + "\\l");
	}

	@Override
	public void postVisit(IClass c) {
		this.appendln("}\"");
		this.appendln("];");
		
		String superC = c.getDeclaration().getSuper();
		if (Utility.isNotBuiltIn(superC)){
			this.appendln(Utility.simplifyClassName(c.getName()) + " -> " + Utility.simplifyClassName(superC) + " [arrowhead=\"onormal\"]");
		}
		
		
		for (String i : c.getDeclaration().getInterfaces()) {
			if (Utility.isNotBuiltIn(i)){
				this.appendln(Utility.simplifyClassName(c.getName()) + " -> " + Utility.simplifyClassName(i) + " [arrowhead=\"onormal\",style=\"dashed\"]");
			}
		}
		
		for (String i : c.getUses()) {
			if (Utility.isNotBuiltIn(i)){
				this.appendln(Utility.simplifyClassName(c.getName()) + " -> " + Utility.simplifyClassName(i) + " [style=\"dashed\"]");
			}
		}
		
		for (String i : c.getAssociation()) {
			if (Utility.isNotBuiltIn(i)){
				this.appendln(Utility.simplifyClassName(c.getName()) + " -> " + Utility.simplifyClassName(i));
			}
		}
		
	}

	@Override
	public void visit(String s) {
		this.append(s);
	}

}
