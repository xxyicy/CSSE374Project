package app;

import impl.Clazz;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import visitor.impl.Visitor;
import api.IClass;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;

public class Test {
	public static void main(String[] args) throws Exception {
		
		if (args.length == 0) {
			throw new Exception("No given path");
		}

		List<Class<?>> classes = ClassFinder.find(args[0]);		
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}
		
		
		Visitor v = new Visitor();
		
		
		v.Start();
		
		
		for (String clazz : cs){
			ClassReader reader=new ClassReader(clazz);
			
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,c);
			
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor,c);
			
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor,c);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			
			
			c.accept(v);
			System.out.println(c);
		}
		
		v.end();
		

		
		
	}
}
