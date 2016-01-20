package impl;

import java.util.HashSet;
import java.util.Set;

import visitor.api.IVisitor;
import api.IClass;
import api.IModel;
import api.IRelation;
import app.Utility;

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
	public boolean contains(IRelation other){
		String ofrom =  Utility.simplifyClassName(other.getFrom());
		String oto =  Utility.simplifyClassName(other.getTo());
		for( IRelation r : this.relations){	
			String rfrom =  Utility.simplifyClassName(r.getFrom());
			String rto =  Utility.simplifyClassName(r.getTo());

			if (r.getType().equals("association") && rfrom.equals(ofrom) && rto.equals(oto)){
				return true;
			}
		}
		return false;	
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
//		this.relations = Utility.removeRelationNotInPackage(this);
		return this.relations;
	}

	@Override
	public void addClass(IClass c) {
		this.classes.add(c);

	}

	@Override
	public void addRelation(IRelation r) {
		if(this.checkRelation(r) && !this.contains(r)){
			this.relations.add(r);
		}
	}
	
	private boolean checkRelation(IRelation r){
		return !r.getFrom().contains("$") && !r.getTo().contains("$");
	}



	


	




}
