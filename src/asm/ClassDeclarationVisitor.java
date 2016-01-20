package asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;




import api.IClass;
import api.IDeclaration;
import api.IModel;
import impl.Declaration;
import impl.Relation;

public class ClassDeclarationVisitor extends ClassVisitor {
	private IClass c;
	private IModel m;
	
	
	public ClassDeclarationVisitor(int api, IClass c,IModel m){
		super(api);
		this.c = c;
		this.m = m;
	}
	
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
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
		for(String i : ins){
			this.m.addRelation(new Relation(name,i,"implements"));
		};
		this.m.addRelation(new Relation(name,superName,"extends"));
		IDeclaration d = new Declaration(type, name);
		this.c.addDeclaration(d);
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
