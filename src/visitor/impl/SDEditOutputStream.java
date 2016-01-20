package visitor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.IMethod;
import app.Utility;
import visitor.api.ISDVisitor;

public class SDEditOutputStream extends ISDVisitor {
	private Map<String, String> declaration;
	private List<String> existedClass;
	private StringBuffer classes;
	private StringBuffer content;
	private int count = 0;

	public SDEditOutputStream() {
		this.declaration = new HashMap<String, String>();
		this.existedClass = new ArrayList<String>();
		this.content = new StringBuffer();
		this.classes = new StringBuffer();
	}

	@Override
	public String toString() {
		return this.classes.toString() + "\n" + this.content.toString();
		
	}

	@Override
	public void visit(IMethod m) {
		addClassName(m.getClassName(), m.getName());

		if (m.getParent() != null) {
			addMessage(m.getParent(), m);
		}

	}

	private void addClassName(String className, String methodName) {
		String simplifiedClassName = Utility.simplifyClassName(className);
		if (this.declaration.get(simplifiedClassName) == null) {

			if (methodName.equals("init")) {
				classes.append("/");
			}else{
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
