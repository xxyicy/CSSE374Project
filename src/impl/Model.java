package impl;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import visitor.api.IVisitor;
import api.IClass;
import api.IMethod;
import api.IMethodRelation;
import api.IModel;
import api.IRelation;
import app.Utility;

public class Model implements IModel {
	private Set<IClass> classes = new HashSet<IClass>();
	private Set<IRelation> relations = new HashSet<IRelation>();
	private Hashtable<IMethod,IMethodRelation> methodRelations = new Hashtable<IMethod,IMethodRelation>();
	
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
		for(IMethodRelation m: this.methodRelations.values()){
			result+= m.toString();
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

	@Override
	public Set<IMethodRelation> getMethodRelation() {
		// TODO Auto-generated method stub
		return (Set<IMethodRelation>) this.methodRelations.values();
	}

	@Override
	public void addMethodRelation(IMethod m, IMethodRelation mr) {
		// TODO Auto-generated method stub
		this.methodRelations.put(m, mr);
		
	}

	@Override
	public IMethodRelation getMethodRelationbyName(String name) {
		int lastDotIndex = name.lastIndexOf(".");
		String className = name.substring(0, lastDotIndex);
		className.replace("/", ".");
		String methodNameAndParams = name.substring(lastDotIndex+1);
		int firstparIndex = methodNameAndParams.lastIndexOf("(");
		String method = methodNameAndParams.substring(0, firstparIndex);
		String params = methodNameAndParams.substring(firstparIndex+1, methodNameAndParams.length()-1);
		String[] paramArr = params.split(",");
		String[] paramTypeArr = new String[paramArr.length];
		for (int i=0;i<paramArr.length;i++){
			paramTypeArr[i] = paramArr[i].split(" ")[0];
			int index = paramTypeArr[i].lastIndexOf("<");
			if (index > 0){
				paramTypeArr[i] = paramTypeArr[i].substring(0, index);
			}
		}
		
		for (IMethodRelation m: this.methodRelations.values()){
			String newClassName = m.getFrom().getClassName().replace("/", ".");
			if (m.getFrom().getName().equals(method) && newClassName.equals(className)){
				if (m.getFrom().getParamTypes().size() != paramTypeArr.length){
					continue;
				}
				boolean isTheSame = true;
				for (int i=0;i<m.getFrom().getParamTypes().size();i++){
					String param = Utility.simplifyClassName(m.getFrom().getParamTypes().get(i));
					if (!param.equals(paramTypeArr[i])){
						isTheSame = false;
					}
				}
				if (isTheSame){
					return m;
				}
			}
		}
		
		return null;
	}


	




}
