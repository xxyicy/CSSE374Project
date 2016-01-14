package app;

import impl.Clazz;
import impl.Model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import visitor.impl.SDEditOutputStream;
import api.IClass;
import api.IModel;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;

public class AppForSequenceDiagram {
	public static void main(String[] args) throws Exception {
		
		if (args.length < 2) {
			throw new Exception("No given class");
		}
			
		
		
		List<Class<?>> classes = ClassFinder.find(args[0]);		
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}

		IModel m = new Model();	
		
		
		
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
			
			
			if(!c.getName().contains("$")){
				m.addClass(c);
			}		
		}
		
		int depth = 5;
		if(args.length==3){
			depth = Integer.parseInt(args[2]);
		}
		
		SDEditOutputStream v = new SDEditOutputStream(depth,args[1],m.getMethodRelation());
		
	
		// Tell the Reader to use our (heavily decorated) ClassVisitor to visit the class
		
		
		PrintWriter writer = new PrintWriter("./output/SequenceDiagramOutput.txt");
		writer.print(v.toString());
		writer.close();
		
		System.out.println(v.toString());
	}
}