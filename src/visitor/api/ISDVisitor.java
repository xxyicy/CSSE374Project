package visitor.api;

import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;
import api.IRelation;

public abstract class ISDVisitor implements IVisitor{
	@Override
	public void visit(IClass c) {
		// do nothing
	}

	@Override
	public void visit(IRelation r) {
		// do nothing
	}

	@Override
	public void visit(String s) {
		// do nothing
	}

	@Override
	public abstract void visit(IMethod m); 

	@Override
	public void visit(IDeclaration d) {
		// do nothing
	}

	@Override
	public void visit(IField f) {
		// do nothing
	}

	@Override
	public void postVisit(IClass c) {
		// do nothing
	}

}
