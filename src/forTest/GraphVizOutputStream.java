package forTest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public class GraphVizOutputStream extends IOutputStream {
	private final IVisitor visitor;
	private StringBuffer result;

	public GraphVizOutputStream(OutputStream out) throws IOException {
		super(out);
		this.visitor = new GraphVizVistor();
		this.result = new StringBuffer();
		this.setupPreVisitClass();
		this.setupVisitClass();
		this.setupPostVisitClass();
		this.setupVisitMethod();
		this.setupVisitField();
		this.setupVisitDeclaration();
		this.setupVisitRelation();
	}

	private void write(String m) {
		try {
			super.write(m.getBytes());
			result.append(m);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeln(String m) {
		try {
			super.write(m.getBytes());
			super.write('\n');
			result.append(m + "\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(IModel m) {
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}

	public String toString() {
		return this.result.toString();
	}

	@Override
	public void start() {
		this.writeln("digraph G {");
		this.writeln("fontname = \"Avenir Book\"");
		this.writeln("fontsize = 10");

		this.writeln("node [");
		this.writeln("fontname = \"Avenir Book\"");
		this.writeln("fontsize = 10");
		this.writeln("shape = \"record\"");
		this.writeln("]");

		this.writeln("edge [");
		this.writeln("fontname = \"Avenir Book\"");
		this.writeln("fontsize = 10");
		this.writeln("]");
	}

	@Override
	public void end() {
		this.writeln("}");
	}

	private void setupPreVisitClass() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IClass c = (IClass) t;

				writeln(Utility.simplifyClassName(c.getName()) + " [");
				writeln("shape=\"record\",");
				if (c.getTags().contains("Singleton")) {
					writeln("color=\"blue\"");
				}
				if (c.getTags().contains("decorator") || c.getTags().contains("component")) {
					writeln("style=\"filled\"");
					writeln("fillcolor=\"green\"");
				}
				if (c.getTags().contains("adapter") || c.getTags().contains("adaptee")
						|| c.getTags().contains("target")) {
					writeln("style=\"filled\"");
					writeln("fillcolor=\"red\"");
				}
				if (c.getTags().contains("Component") || c.getTags().contains("Composite")
						|| c.getTags().contains("Leaf")) {
					writeln("style=\"filled\"");
					writeln("fillcolor=\"yellow\"");
				}
				write("label = \"{");
			}
		};
		this.visitor.addVisit(VisitType.PreVisit, IClass.class, command);
	}

	private void setupVisitClass() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				write("|");
			}
		};
		this.visitor.addVisit(VisitType.Visit, IClass.class, command);
	}

	private void setupPostVisitClass() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				writeln("}\"");
				writeln("];");
			}
		};
		this.visitor.addVisit(VisitType.PostVisit, IClass.class, command);
	}

	private void setupVisitDeclaration() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				IDeclaration d = (IDeclaration) t;
				if (d.getType() == "interface") {
					write("\\<\\<" + d.getType() + "\\>\\>" + "\\n" + Utility.simplifyClassName(d.getName()));
				} else {
					write(Utility.simplifyClassName(d.getName()));
				}
				for (String s : d.getTags()) {
					write("\\n\\<\\<" + s + "\\>\\>\\n");
				}
			}
		};
		this.visitor.addVisit(VisitType.Visit, IDeclaration.class, command);
	}

	public void setupVisitMethod() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IMethod m = (IMethod) t;
				write(m.getAccess() + " " + m.getName() + "(");

				if (!m.getParamTypes().isEmpty()) {
					int count = 0;

					List<String> types = m.getParamTypes();
					for (int i = 0; i < types.size() - 1; i++) {
						String s = types.get(i);
						String tmp = "arg" + count;
						count++;
						write(tmp + ":" + Utility.simplifyType(s) + ",");
					}
					write("arg" + count + ":" + Utility.simplifyType(types.get(types.size() - 1)));
				}

				write(")" + " : " + Utility.simplifyType(m.getReturnType()));
				if (!m.getExceptions().isEmpty()) {
					write(" throws ");
					List<String> exceps = m.getExceptions();
					for (int i = 0; i < exceps.size() - 1; i++) {
						String s = exceps.get(i);
						write(Utility.simplifyClassName(s) + ",");
					}
					write(Utility.simplifyClassName(exceps.get(exceps.size() - 1)));
				}
				write("\\l");
			}
		};
		this.visitor.addVisit(VisitType.Visit, IMethod.class, command);
	}

	private void setupVisitField() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				IField f = (IField) t;
				String line = String.format("%s %s : %s\\l", f.getAccess(), f.getName(),
						Utility.simplifyType(f.getType()));
				write(line);
			}

		};
		this.visitor.addVisit(VisitType.Visit, IField.class, command);
	}

	public void setupVisitRelation() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				IRelation r = (IRelation) t;
				if (r.isVisible()) {
					String structure = "";
					switch (r.getType()) {
					case "extends":
						structure = " [arrowhead=\"onormal\"";
						break;
					case "implements":
						structure = " [arrowhead=\"onormal\",style=\"dashed\"";
						break;
					case "use":
						structure = " [arrowhead=\"vee\",style=\"dashed\"";
						break;
					case "association":
						structure = " [arrowhead=\"vee\"";
						break;
					}
					String label = "]";
					if (r.getDes() != null) {
						label = ",label=\"" + r.getDes() + "\"]";
					}

					writeln(Utility.simplifyClassName(r.getFrom()) + " -> " + Utility.simplifyClassName(r.getTo())
							+ structure + label);
				}
			}

		};
		this.visitor.addVisit(VisitType.Visit, IRelation.class, command);
	}

}
