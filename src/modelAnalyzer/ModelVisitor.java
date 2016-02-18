package modelAnalyzer;

import java.util.ArrayList;
import java.util.List;

import api.IClass;
import api.IField;
import api.IMethod;
import api.IModel;
import api.IPattern;
import api.IRelation;

public class ModelVisitor extends AbstractModelVisitor {
	private List<IPattern> patterns;
	
	
	public ModelVisitor(IModel m){
		super(m);
		this.patterns = new ArrayList<IPattern>();
		
	}
	
	public void setPatterns(List<IPattern> patterns){
		this.patterns = patterns;
	}
	
	
	@Override
	protected void visitClass(IClass c) {
		c.setVisible(false);
		if(c.getName().startsWith("java")){
			c.setVisible(true);
		}
		
	}

	@Override
	protected void visitMethod(IMethod m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void visitField(IField f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void visitPattern(IPattern p) {
		System.out.println("Visiting pattern");
		if(this.patterns.contains(p)){
			for(IClass c: p.getClasses()){
				c.setVisible(true);
			}
		}
		
	}

	@Override
	protected void visitRelation(IRelation r) {
		// TODO Auto-generated method stub
		
	}
	
	
	private IClass getClassByName(IModel m, String name) {
		name = name.replaceAll(".", "/");
		for (IClass c : m.getClasses()) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	
}
