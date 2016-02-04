package forTest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SDEditOutputStream extends IOutputStream {
	private final IVisitor visitor;
	private Map<String, String> declaration;
	private List<String> existedClass;
	private StringBuffer classes;
	private StringBuffer content;
	private int count = 0;

	public SDEditOutputStream(OutputStream out) throws IOException {
		super(out);
		this.visitor = new Visitor();
		this.declaration = new HashMap<String, String>();
		this.existedClass = new ArrayList<String>();
		this.content = new StringBuffer();
		this.classes = new StringBuffer();
		this.setupVisitMethod();
	}

	

	private void write(String m) {
		try {
			super.write(m.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeln(String m) {
		try {
			super.write(m.getBytes());
			super.write('\n');
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(IMethod m) {
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
		write(classes.toString());
		writeln("");
		write(content.toString());
	}

	private void setupVisitMethod() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				IMethod m = (IMethod) t;
				addClassName(m.getClassName(), m.getName());

				if (m.getParent() != null) {
					addMessage(m.getParent(), m);
				}
			}

		};
		this.visitor.addVisit(VisitType.Visit, IMethod.class, command);
	}

	public String toString() {
		return classes.toString() + "\n" + content.toString();
	}

	private void addClassName(String className, String methodName) {
		String simplifiedClassName = Utility.simplifyClassName(className);
		if (this.declaration.get(simplifiedClassName) == null) {

			if (methodName.equals("init")) {
				classes.append("/");
			} else {
				existedClass.add(simplifiedClassName);
			}
			String newName = "arg" + count;
			this.declaration.put(simplifiedClassName, newName);
			classes.append(newName + ":" + simplifiedClassName + "\n");
			count++;
		}
	}

	private void addMessage(IMethod caller, IMethod callee) {
		String simplifiedCallerClassName = Utility.simplifyClassName(caller.getClassName());
		String simplifiedCalleeClassName = Utility.simplifyClassName(callee.getClassName());

		if (callee.getName().equals("init")) {
			String message = this.declaration.get(simplifiedCalleeClassName) + "." + "new\n";
			if (!this.content.toString().contains(message) && !this.existedClass.contains(simplifiedCalleeClassName)) {
				this.content.append(this.declaration.get(simplifiedCallerClassName) + ":");
				this.content.append(message);
				this.existedClass.add(simplifiedCalleeClassName);
			}
		} else {
			this.content.append(this.declaration.get(simplifiedCallerClassName) + ":");
			this.content.append(this.declaration.get(simplifiedCalleeClassName) + ".");
			this.content.append(callee.getName() + "(");
			List<String> params = callee.getParamTypes();
			for (int j = 0; j < params.size() - 1; j++) {
				this.content.append(Utility.simplifyType(params.get(j)) + ",");
			}
			if (!params.isEmpty())
				this.content.append(Utility.simplifyType(params.get(params.size() - 1)) + ")");
			else {
				this.content.append(")");
			}
			this.content.append(":");
			this.content.append(Utility.simplifyType(callee.getReturnType()) + "\n");
		}
	}

}
