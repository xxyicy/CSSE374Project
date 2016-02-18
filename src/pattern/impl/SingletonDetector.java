package pattern.impl;

import pattern.api.IDetector;
import visitor.api.ITraverser;
import visitor.api.IVisitMethod;
import visitor.api.IVisitor;
import visitor.api.VisitType;
import visitor.api.Visitor;
import impl.Pattern;
import api.IClass;
import api.IField;
import api.IMethod;
import api.IModel;
import api.IPattern;

public class SingletonDetector implements IDetector {
	private final IVisitor visitor;
	private String className;
	private boolean staticField;
	boolean privateConstructor;
	boolean staticMethod;
	int requireGetInstance;

	public SingletonDetector(int requireGetInstance) {
		visitor = new Visitor();
		this.setupVisitMethod();
		this.setupVisitField();
		this.requireGetInstance = requireGetInstance;
	}
	
	
	@Override
	public String toString(){
		return "Singleton Pattern";
	}

	@Override
	public void detect(IModel m) {
		for (IClass c : m.getClasses()) {
			className = c.getName().replaceAll("/", ".");
			staticField = false;
			privateConstructor = true;
			staticMethod = false;
			c.accept(visitor);
			if (staticField && privateConstructor && staticMethod) {
				IPattern p = new Pattern("Singleton");
				p.addClass(c);
				c.addTag("Singleton");
				m.addPattern(p);
			}
		}
	}

	private void setupVisitMethod() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				IMethod m = (IMethod) t;
				if (m.getName().equals("<init>") && !m.getAccess().equals("-")) {
					privateConstructor = false;
				}
				if (m.isStatic() && m.getReturnType().equals(className) && m.getAccess().equals("+")) {
					staticMethod = true;
				}
			}
		};
		this.visitor.addVisit(VisitType.Visit, IMethod.class, command);
	}

	private void setupVisitField() {
		IVisitMethod command = new IVisitMethod() {

			@Override
			public void execute(ITraverser t) {
				IField f = (IField) t;
				if (f.isStatic() && f.getAccess().equals("-") && className.equals(f.getType())) {
					staticField = true;
				}
			}
		};
		this.visitor.addVisit(VisitType.Visit, IField.class, command);
	}

//	private boolean isSingleton(IClass c) {
//		for (IField f : c.getFields()) {
//			if (f.isStatic() && f.getAccess().equals("-") && className.equals(f.getType())) {
//				staticField = true;
//			}
//		}
//
//		for (IMethod m : c.getMethods()) {
//			if (m.getName().equals("<init>") && !m.getAccess().equals("-")) {
//				privateConstructor = false;
//			}
//			if (m.isStatic() && m.getReturnType().equals(className) && m.getAccess().equals("+")) {
//				staticMethod = true;
//			}
//		}
//		return staticField && privateConstructor && staticMethod;
//	}

}
