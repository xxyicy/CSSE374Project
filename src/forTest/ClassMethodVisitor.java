package forTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import api.IClass;
import api.IMethod;
import api.IMethodRelation;
import api.IModel;
import api.IRelation;
import impl.Method;
import impl.MethodRelation;
import impl.Relation;

public class ClassMethodVisitor extends ClassVisitor {
	private IClass c;
	private IModel m;

	public ClassMethodVisitor(int api, IClass c, IModel m) {
		super(api);
		this.c = c;
		this.m = m;
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, IClass c, IModel m) {
		super(api, decorated);
		this.c = c;
		this.m = m;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		String acc;
		String type;
		List<String> args;
		List<String> exps = exceptions == null ? new ArrayList<String>() : Arrays.asList(exceptions);

		acc = addAccessLevel(access);
		type = addReturnType(desc);

		IRelation typeUse = new Relation(this.c.getName(), type, "use");
		if (!type.equals("void") && !this.m.contains(typeUse)) {
			this.m.addRelation(typeUse);
		}

		args = addArguments(desc);
		for (String arg : args) {
			IRelation argUse = new Relation(this.c.getName(), arg, "use");
			if (!this.m.contains(argUse)) {
				this.m.addRelation(argUse);
			}
		}

		String self = this.c.getName();
		
		Method method = new Method(name, type, acc, args, exps,this.c.getName());
		this.c.addMethod(method);
		IMethodRelation methodRelation = new MethodRelation(method);
		this.m.addMethodRelation(method,methodRelation);
		MethodVisitor instMv = new MethodVisitor(Opcodes.ASM5, toDecorate) {

			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
				
				if (name.equals("<init>")) {
					
					IRelation r = new Relation(self, owner, "use");
					Boolean hasSuper = true;
					for (IRelation r1 : ClassMethodVisitor.this.m.getRelations()) {
						if (r1.getType().equals("extends") && r1.getFrom().equals(self) && r1.getTo().equals(owner))
							hasSuper = false;
					}
					if (hasSuper){
						ClassMethodVisitor.this.m.addRelation(r);
						String accCalled = "";
						String typeCalled = addReturnType(desc);
						List<String> args = addArguments(desc);
						IMethod called = new Method(name,typeCalled,accCalled,args,new ArrayList<String>(),owner);
						methodRelation.addMethod(called);
					}
						
				}else{
					String accCalled = "";
					String typeCalled = addReturnType(desc);
					List<String> args = addArguments(desc);
					IMethod called = new Method(name,typeCalled,accCalled,args,new ArrayList<String>(),owner);
					methodRelation.addMethod(called);
				}		
				
				
			}
		};

	
		return instMv;
	}

	String addAccessLevel(int access) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "+";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "#";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "-";
		} else {
			level = "";
		}
		return level;

	}

	String addReturnType(String desc) {
		return Type.getReturnType(desc).getClassName();
	}

	List<String> addArguments(String desc) {
		List<String> result = new ArrayList<String>();
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();

			result.add(arg);
		}
		return result;
	}

}
