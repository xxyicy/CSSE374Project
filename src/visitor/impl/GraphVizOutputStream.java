package visitor.impl;

import visitor.api.IVisitor;
import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;
import api.IRelation;
import app.Utility;

public class GraphVizOutputStream implements IVisitor {
	private StringBuffer b;

	public GraphVizOutputStream() {
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

	private void appendln(String s) {
		this.b.append(s);
		this.b.append("\n");
	}

	private void append(String s) {
		this.b.append(s);
	}

	@Override
	public void visit(IClass c) {
		this.appendln(Utility.simplifyClassName(c.getName()) + " [");
		this.appendln("shape=\"record\",");
		if (c.getDeclaration().isSingleton()){
			this.appendln("color=\"blue\"");
		}
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

		this.append(")" + " : " + Utility.simplifyType(m.getReturnType()));
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
		if (d.getType() == "interface") {
			this.append("\\<\\<" + d.getType() + "\\>\\>" + "\\n" + Utility.simplifyClassName(d.getName()));
		}else{
			this.append(Utility.simplifyClassName(d.getName()));
			if (d.isSingleton()){
				this.append("\\n\\<\\<Singleton\\>\\>\\n");
			}
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
	}

	@Override
	public void visit(String s) {
		this.append(s);
	}

	@Override
	public void visit(IRelation r) {
		String structure = "";
		switch (r.getType()){
		case "extends":
			structure = " [arrowhead=\"onormal\"]";
			break;
		case "implements":
			structure = " [arrowhead=\"onormal\",style=\"dashed\"]";
			break;
		case "use":
			structure = " [arrowhead=\"vee\",style=\"dashed\"]";
			break;
		case "association":
			structure = " [arrowhead=\"vee\"]";
			break;
		}
		if (Utility.isNotBuiltIn(r.getTo())){
			this.appendln(Utility.simplifyClassName(r.getFrom()) + " -> " + Utility.simplifyClassName(r.getTo()) + structure);
		}
	}


}
