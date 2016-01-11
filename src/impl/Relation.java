package impl;

import visitor.api.IVisitor;
import api.IRelation;
import app.Utility;

public class Relation implements IRelation {
	private String from;
	private String to;
	private String type;
	
	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
	
	public Relation(String from,String to,String type){
		this.from = from;
		this.to = to;
		this.type = type;
	}
	
	@Override
	public String toString(){
		return "Relation {from:"+from+" to: "+to+" type: "+type+ " }"+"\n";
	}
	
	@Override
	public String getFrom() {
		
		return from;
	}
	@Override
	public String getTo() {
	
		return to;
	}
	@Override
	public String getType() {
	
		return type;
	}

	@Override
	public int hashCode() {
		String from = Utility.simplifyClassName(this.from);
		String to = Utility.simplifyClassName(this.to);
		
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		
		Relation other = (Relation) obj;
		
		
		
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!Utility.simplifyClassName(from).equals(Utility.simplifyClassName(other.from)))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!Utility.simplifyClassName(to).equals(Utility.simplifyClassName(other.to)))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		Relation other = (Relation) obj;
//		return (this.from.equals(other.from) && this.to.equals(other.to) && this.type.equals(other.type));
//	}	
}
