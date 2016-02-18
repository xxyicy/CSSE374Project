package modelAnalyzer;


import java.util.ArrayList;
import java.util.List;

import observer.api.Notifier;
import observer.api.Observer;
import api.IClass;
import api.IField;
import api.IMethod;
import api.IModel;
import api.IPattern;
import api.IRelation;
import visitor.api.ITraverser;
import visitor.api.IVisitMethod;
import visitor.api.VisitType;
import visitor.api.Visitor;

public abstract class AbstractModelVisitor extends Visitor implements Notifier {
	protected IModel m;
	private List<Observer> observers;
	
	public AbstractModelVisitor(IModel m){
		this.m = m;
		this.observers = new ArrayList<Observer>();
		this.setupVisitClass();
		this.setupVisitField();
		this.setupVisitMethod();
		this.setupVisitPattern();
		this.setupVisitRelation();
	}
	
	
	public void visitModel(){
		m.accept(this);
	}
	
	
	
	private void setupVisitClass() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IClass c = (IClass) t;
				AbstractModelVisitor.this.visitClass(c);;
			}
		};
		this.addVisit(VisitType.Visit, IClass.class, command);
	}
	
	private void setupVisitMethod() {
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IMethod m = (IMethod) t;
				AbstractModelVisitor.this.visitMethod(m);
			}
		};
		this.addVisit(VisitType.Visit, IMethod.class, command);
	}
	
	private void setupVisitField(){
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IField m = (IField) t;
				AbstractModelVisitor.this.visitField(m);
			}
		};
		this.addVisit(VisitType.Visit, IField.class, command);
	}
	
	private void setupVisitRelation(){
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IRelation m = (IRelation) t;
				AbstractModelVisitor.this.visitRelation(m);
			}
		};
		this.addVisit(VisitType.Visit, IRelation.class, command);
	}
	
	private void setupVisitPattern(){
		IVisitMethod command = new IVisitMethod() {
			@Override
			public void execute(ITraverser t) {
				IPattern m = (IPattern) t;
				AbstractModelVisitor.this.visitPattern(m);
			}
		};
		this.addVisit(VisitType.Visit, IPattern.class, command);
	}
	
	
	
	protected abstract void visitClass(IClass c);
	
	protected abstract void visitMethod(IMethod m);
	
	protected abstract void visitField(IField f);
	
	protected abstract void visitPattern(IPattern p);
	
	protected abstract void visitRelation(IRelation r);
	
	
	
	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);

	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);

	}

	@Override
	public void notifyObservers(Object data) {
		for (Observer o : this.observers) {
			o.update(data);
		}

	}
	
	
}
