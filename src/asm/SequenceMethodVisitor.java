package asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import api.IMethod;
import impl.Method;

public class SequenceMethodVisitor extends ClassVisitor {
	private IMethod m;
	private String className;


	public SequenceMethodVisitor(int api, IMethod m,String className) {
		super(api);
		this.m = m;
		this.className = className;
	}

	public SequenceMethodVisitor(int api, ClassVisitor decorated, IMethod m,String className) {
		super(api, decorated);
		this.m = m;
		this.className = className;
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
		args = addArguments(desc);
	
		
		
		Method method = new Method(name, type, acc, args, exps,className);
		
//		
//		System.out.println(method);
//		System.out.println("passed in: "+this.m);
//		System.out.println("found method");
		
		
		

		//check if it's the passed in one
		if(method.compareMethod(this.m)){
			//update the return type of m since it's initially empty string;
	
			this.m.setReturnType(type);
			MethodVisitor instMv = new MethodVisitor(Opcodes.ASM5, toDecorate) {
				@Override
				public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
					//looks like a <init> method would initialize all the super class instance of the current one?
					if (owner.equals("java/lang/Object")) {
						return;
					}
				
					String className = owner;
					String returnType = addReturnType(desc);
					List<String> args = addArguments(desc);
					IMethod called = new Method(name,returnType,"NN",args,new ArrayList<String>(),className);
					SequenceMethodVisitor.this.m.addCall(called);
					
				}
			};

		
			return instMv;
			
			
		}
		return toDecorate;
	
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
