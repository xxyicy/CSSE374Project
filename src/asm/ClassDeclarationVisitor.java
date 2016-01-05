package asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;


import api.IClass;
import api.IDeclaration;
import impl.Declaration;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private IClass c;
	
	public ClassDeclarationVisitor(int api, IClass c){
		super(api);
		this.c = c;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		
		System.out.println("Class: "+name+" extends "+superName+" implements "+Arrays.toString(interfaces));
		
		String type;
		
		if((access & Opcodes.ACC_INTERFACE) != 0){
			type = "interface";
		}
		else if((access & Opcodes.ACC_ABSTRACT ) != 0){
			type = "abstract";
		}
		else{
			type = "class";
		}
		
		List<String> ins = interfaces == null ? new ArrayList<String>() : Arrays.asList(interfaces); 
		
		IDeclaration d = new Declaration(type, name, superName, ins);
		this.c.addDeclaration(d);
		
		super.visit(version, access, name, signature, superName, interfaces);
		
	}
}
