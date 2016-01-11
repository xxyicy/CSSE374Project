package forTest;

import java.util.HashSet;
import java.util.Set;

import visitor.api.IVisitor;
import api.IClass;
import api.IModel;
import api.IRelation;

public class Model implements IModel {
	private Set<IClass> classes = new HashSet<IClass>();
	private Set<IRelation> relations = new HashSet<IRelation>();

	@Override
	public void accept(IVisitor v) {
		for (IClass c: this.classes) {
			c.accept(v);
		}
		for (IRelation r: this.relations) {
			r.accept(v);
		}
	}

	@Override
	public String toString(){
		String result = "";
		for(IClass c : this.classes){
			result += c.toString();
		}
		
		for(IRelation r : this.relations){
			result += r.toString();
		}
		return result;
	}
	
	@Override
	public Set<IClass> getClasses() {

		return this.classes;
	}

	@Override
	public Set<IRelation> getRelations() {
		return this.relations;
	}

	@Override
	public void addClass(IClass c) {
		this.classes.add(c);

	}

	@Override
	public void addRelation(IRelation r) {
		this.relations.add(r);

	}

}
