package visitor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import api.IMethod;
import api.IMethodRelation;
import app.Utility;

public class SDEditOutputStream {
	private Map<String, String> declaration;
	private List<String> initializedArray;
	private StringBuffer classes;
	private StringBuffer content;
	private int count = 0;
	private int depth;
	private IMethod startMethod;

	public SDEditOutputStream(int depth, IMethod method) {
		this.depth = depth;
		this.startMethod = method;
		this.declaration = new HashMap<String, String>();
		this.content = new StringBuffer();
		this.classes = new StringBuffer();
		this.initializedArray = new ArrayList<String>();
		parse(this.startMethod, 0);
	}

	public void parse(IMethod start, int depth) {
		if (depth == this.depth) {
			return;
		}

		addClassName(start.getClassName(), "");

		List<IMethod> start_to = start.getCalls();
		for (int i = 0; i < start_to.size(); i++) {
			this.content.append(this.declaration.get(Utility.simplifyClassName(start.getClassName())));
			this.content.append(":");

			// check if there is new class appears, if is add to declaration
			// string
			addClassName(start_to.get(i).getClassName(), start_to.get(i).getName());

			this.content.append(this.declaration.get(Utility.simplifyClassName(start_to.get(i).getClassName())));
			this.content.append(".");
			if (start_to.get(i).getName().equals("init")) {
				this.content.append("new\n");
				this.initializedArray.add(start_to.get(i).getClassName());
			} else {
				this.content.append(Utility.simplifyClassName(start_to.get(i).getName()) + "(");
				List<String> params = start_to.get(i).getParamTypes();
				for (int j = 0; j < params.size() - 1; j++) {
					this.content.append(Utility.simplifyType(params.get(j)) + ",");
				}
				if (!params.isEmpty())
					this.content.append(Utility.simplifyType(params.get(params.size() - 1)) + ")");
				else {
					this.content.append(")");
				}
				this.content.append(":");
				this.content.append(Utility.simplifyClassName(start_to.get(i).getType()) + "\n");
			}

			parse(start_to.get(i), depth++);

		}
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

	@Override
	public String toString() {
		return this.classes.toString() + "\n" + this.content.toString();

	}

}
