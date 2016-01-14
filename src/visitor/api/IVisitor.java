package visitor.api;

import api.IClass;
import api.IDeclaration;
import api.IField;
import api.IMethod;
import api.IMethodRelation;
import api.IRelation;

public interface IVisitor {
	void visit(IClass c);
	void visit(IRelation r);
	
	void visit(String s);
	
	
	void visit(IMethod m);
	void visit(IDeclaration d);
	void visit(IField f);
	void visit(IMethodRelation mr);
	void postVisit(IClass c);
}
