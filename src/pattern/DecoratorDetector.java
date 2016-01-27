package pattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import api.IClass;
import api.IField;
import api.IModel;
import api.IRelation;


public class DecoratorDetector implements IDetector {

	@Override
	public void detect(IModel m) {
		for(IClass c : m.getClasses()){
			IField f = this.composeSuper(c, m);
		
			
		}
	}

	
	
	
	private IField composeSuper(IClass c,IModel m){
		Set<IRelation> rs = m.getRelations();
		Set<String> supers = this.getSuperClasses(c, rs);
		for(IField f: c.getFields()){
			String target = f.getType().replaceAll("[.]", "/");
			if(supers.contains(target)){
				System.out.println(c.getName()+" composes of type :"+f.getType());
				return f;
			}
		}
		return null;
	}
	
	private Set<String> getSuperClasses(IClass c, Set<IRelation> r ){
		Set<String> result = new HashSet<String>();
		String name = c.getName();
		while(true){
			String tmp = this.getSuper(name, r);
			if(tmp  != null){
				result.add(tmp);
				name = tmp;
			}
			else{
				break;
			}	
		}
		return result;
		
	}
	
	private String getSuper(String c, Set<IRelation> rs){
		for (IRelation r : rs){
			if(r.getFrom().equals(c) && r.getType().equals("extends")  ){
				return r.getTo();
			}
		}
		return null;
	}
}
