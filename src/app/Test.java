package app;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import visitor.api.IVisitor;
import visitor.impl.Visitor;
import api.IClass;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import impl.Class;


public class Test {

	public static void main(String[] args) throws IOException {
		ClassReader reader=new ClassReader("java.lang.String");
		
		IClass c = new Class();
		// make class declaration visitor to get superclass and interfaces
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,c);
		
		// DECORATE declaration visitor with field visitor
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor,c);
		
		// DECORATE field visitor with method visitor
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor,c);

		// TODO: add more DECORATORS here in later milestones to accomplish specific tasks
		
		
		
		// Tell the Reader to use our (heavily decorated) ClassVisitor to visit the class
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		
		
		Visitor v = new Visitor();
		v.Start();
		c.accept(v);
		v.end();
		
		System.out.println(v.toString());
	}

}
