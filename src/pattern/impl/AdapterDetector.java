package pattern.impl;

import impl.Clazz;
import impl.Pattern;
import impl.Relation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import pattern.api.IDetector;
import api.IClass;
import api.IField;
import api.IMethod;
import api.IModel;
import api.IPattern;
import api.IRelation;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;

public class AdapterDetector implements IDetector {

	@Override
	public void detect(IModel m) throws Exception {
		String adaptee = null;
		IClass adapter = null;
		IClass ITarget = null;

		
		for (IClass c : m.getClasses()) {
			Set<String> result = this.getInterfaces(c, m.getRelations());
			if (result.size() == 1) {
				String first = result.iterator().next();
				System.out.println(first);
				// find the target interface
				IClass target = this.getClassByName(m, first);
				
				if (target == null) {
					throw new Exception("unexpected situation");
				}
				// collect information about methods in the target interface
				List<String> targetMethods = new ArrayList<String>();
				
				for (IMethod method : target.getMethods()) {
					targetMethods.add(method.getName());
				}

				
				List<String> fieldTypes = new ArrayList<String>();
				for (IField f : c.getFields()) {
					String fieldType = f.getType();
					fieldType = fieldType.replaceAll("[.]", "/");
					fieldTypes.add(fieldType);
				}

				

				// used to store all the ClassName of methods invoked in those
				// methods that also exist in super interface
				List<String> calledClasses = null;
				for (IMethod meth : c.getMethods()) {
					if (targetMethods.contains(meth.getName())) {
						System.out.println(meth.printCallChains(0));

						List<String> tmp = new ArrayList<String>();
						for (IMethod called : meth.getCalls()) {
							tmp.add(called.getClassName());
						}

						if (calledClasses == null) {
							calledClasses = tmp;
						} else {
							calledClasses = this.intersection(calledClasses,
									tmp);
						}
					}
				}

				List<String> adaptees = this.intersection(calledClasses,
						fieldTypes);
				

				if (adaptees.isEmpty()) {
					continue;
				}

				if (adaptees.size() > 1) {
					throw new Exception("Detect more than 1 adaptee?");
				}

				adaptee = adaptees.get(0);
				System.out.println("adaptee found is " + adaptee);
				
				if(!this.checkConstructor(adaptee, c)){
					adaptee = null;
					break;
				}
				
				
				adapter = c;
				
				ITarget = target;

				break;

			}
		}

		this.constructPattern(adaptee, adapter, ITarget, m);

	}

	
	private boolean checkConstructor(String adaptee, IClass adapter){
		boolean result = false;
		
		for(IMethod m : adapter.getMethods()){
			if(m.getName().equals("init")){
				
				String toFound = adaptee.replaceAll("/", ".");
				if(m.getParamTypes().contains(toFound)){
					result = true;
				}
			}
		}
		return result;
	}
	
	private void constructPattern(String adaptee, IClass adapter,
			IClass ITarget, IModel m) throws IOException {
		
		if (adaptee != null && adapter != null && ITarget != null) {
			System.out.print(adapter.getName() + ":" + ITarget.getName() + ":"
					+ adaptee);
			
			IClass adapteeClass = this.getClassByName(m, adaptee);
			
			
			//possible if adaptee class is not read yet
			if (adapteeClass == null) {

				ClassReader reader = new ClassReader(adaptee);

				IClass clazz = new Clazz();
				// make class declaration visitor to get superclass and
				// interfaces
				ClassVisitor decVisitor = new ClassDeclarationVisitor(
						Opcodes.ASM5, clazz, m);
				// DECORATE declaration visitor with field visitor
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
						decVisitor, clazz, m);
				// DECORATE field visitor with method visitor
				ClassVisitor methodVisitor = new ClassMethodVisitor(
						Opcodes.ASM5, fieldVisitor, clazz, m);

				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

				adapteeClass = clazz;

				m.addClass(adapteeClass);

			}

			adapteeClass.addTag("adaptee");
			adapter.addTag("adapter");
			ITarget.addTag("target");
			
			
			boolean suc = false;
			for (IRelation r : m.getRelations()) {
				if (r.getFrom().equals(adapter.getName())
				&& r.getTo().replaceAll("[.]", "/").equals(adaptee) && r.getType().equals("association")) {
						r.setDes("adapts");
						suc = true;
				}
			}
			if(!suc){
				IRelation ir = new Relation(adapter.getName(),adaptee,"association");
				ir.setDes("adapts");
				m.addRelation(ir);
			}
			
			IPattern p = new Pattern("adapter");
			p.addClass(adapteeClass);
			p.addClass(adapter);
			p.addClass(ITarget);
			m.addPattern(p);
		}
	}

	private List<String> intersection(List<String> a, List<String> b) {
		List<String> r = new ArrayList<String>();
		if(a==null || b ==null){
			return r;
		}
		for (String s : a) {
			if (b.contains(s)) {
				r.add(s);
			}
		}
		return r;
	}

	private IClass getClassByName(IModel m, String name) {
		for (IClass c : m.getClasses()) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	private Set<String> getInterfaces(IClass c, Set<IRelation> rs) {
		Set<String> result = new HashSet<String>();
		String name = c.getName();
		for (IRelation r : rs) {
			if (r.getFrom().equals(name) && r.getType().equals("implements")) {
				result.add(r.getTo());
			}
		}
		return result;
	}

}
