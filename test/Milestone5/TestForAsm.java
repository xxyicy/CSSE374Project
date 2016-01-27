package Milestone5;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IDeclaration;
import api.IModel;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import impl.Clazz;
import impl.Model;
import pattern.DecoratorDetector;
import pattern.IDetector;
import pattern.SingletonDetector;
import visitor.impl.GraphVizOutputStream;

public class TestForAsm {
	private IModel m;
	private IClass c;
	private ClassVisitor visitor;
	private GraphVizOutputStream v;

	@Before
	public void setUp() throws Exception {
		m = new Model();
		c = new Clazz();
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c,
				m);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
				decVisitor, c, m);
		visitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);
		v = new GraphVizOutputStream();
	}
	
	
	@Test
	public void TestDecorator1() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("java/io/InputStreamReader");

		IModel m = new Model();	
		
		IDetector detect = new SingletonDetector(); 		
		for (String clazz : cs){
			ClassReader reader = new ClassReader(clazz);

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
	     detect.detect(m);
		 
		  for(IClass c: m.getClasses()){
			  if(c.getName().equals("java/io/InputStreamReader")){
				  assertEquals(true,c.getTags().contains("decorator"));
			  }
		
		  }
		
	}
	
	@Test
	public void TestDecorator2() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("java/io/OutputStreamWriter");

		IModel m = new Model();	
		
		IDetector detect = new SingletonDetector(); 		
		for (String clazz : cs){
			ClassReader reader = new ClassReader(clazz);

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
	     detect.detect(m);
		 
		  for(IClass c: m.getClasses()){
			  if(c.getName().equals("java/io/OutputStreamWriter")){
				  assertEquals(true,c.getTags().contains("decorator"));
			  }
		
		  }
		
	}
	
	
	@Test
	public void TestDecorator3() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("java/awt/event/MouseAdapter");

		IModel m = new Model();	
		
		IDetector detect = new SingletonDetector(); 		
		for (String clazz : cs){
			ClassReader reader = new ClassReader(clazz);

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
	     detect.detect(m);
		 
		  for(IClass c: m.getClasses()){
			  if(c.getName().equals("java/awt/event/MouseAdapter")){
				  assertEquals(true,c.getTags().contains("adapter"));
			  }
		
		  }
		
	}
}
