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
	private List<String> initializedArray;
	private StringBuffer classes;
	private StringBuffer content;
	private int count = 0;

	public SDEditOutputStream() {
		this.declaration = new HashMap<String, String>();
		this.content = new StringBuffer();
		this.classes = new StringBuffer();
		this.initializedArray = new ArrayList<String>();
	}

	@Override
	public String toString() {
		return this.classes.toString() + "\n" + this.content.toString();

	}

	@Override
	public void visit(IMethod m) {
		addClassName(m.getClassName(), "");
		
		addMessage(m.getParent(), m);

//		List<IMethod> start_to = m.getCalls();
//		for (int i = 0; i < start_to.size(); i++) {
//			addMessage(m, start_to.get(i));
//		}
	}

	private void addClassName(String className, String methodName) {
		String simplifiedClassName = Utility.simplifyClassName(className);
		// String simplifiedClassName = className;
		if (this.declaration.get(simplifiedClassName) == null) {
			if (methodName.equals("init") || this.initializedArray.contains(simplifiedClassName)) {
				classes.append("/");
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
		this.content.append(this.declaration.get(simplifiedCallerClassName));
		this.content.append(":");

//		// check if there is new class appears, if is add to declaration
//		// string
//		addClassName(callee.getClassName(), callee.getName());

		this.content.append(this.declaration.get(simplifiedCalleeClassName));
		this.content.append(".");
		if (callee.getName().equals("init")) {
			this.content.append("new\n");
			this.initializedArray.add(callee.getClassName());
		} else {
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
