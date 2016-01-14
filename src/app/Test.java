package app;

import impl.Clazz;
import impl.Model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import visitor.impl.GraphVizOutputStream;
import api.IClass;
import api.IModel;
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
		
		
		GraphVizOutputStream v = new GraphVizOutputStream();
		IModel m = new Model();
		
		
		v.Start();
		
		
		for (String clazz : cs){
			ClassReader reader=new ClassReader(clazz);
			
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,c,m);
			
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor,c,m);
			
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor,c,m);
			
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			
			
			
			m.addClass(c);
			
		}
		
		Utility.removeRelationNotInPackage(m);
		m.accept(v);
		
		v.end();
		
		
		
		System.out.println(m);

		
		
	}
}
