
<<<<<<< HEAD
package Milestone4;

=======
package Milestone4;
>>>>>>> branch 'master' of https://github.com/xxyicy/CSSE374Project.git
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IMethod;
import api.IModel;
import app.ClassFinder;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import asm.SequenceMethodVisitor;
import impl.Clazz;
import impl.Method;
import impl.Model;
import pattern.DecoratorDetector;
import pattern.IDetector;
import pattern.SingletonDetector;
import visitor.api.ISDVisitor;
import visitor.impl.GraphVizOutputStream;

import visitor.impl.SDEditOutputStream;

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
	public void TestSingleton1() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("java/lang/Runtime");

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
			  //System.out.println(c.getName());
			  if(c.getName().equals("java/lang/Runtime")){
				  assertEquals(true,c.getTags().contains("Singleton"));
			  }
		
		  }
		
	}


=======
import visitor.impl.SDEditOutputStream;

public class TestForAsm {
	
		private IModel m;
		private IClass c;
		private ClassVisitor visitor;
		private GraphVizOutputStream v;
		
		@Before
		public void setUp() throws Exception {
			m = new Model();
			c = new Clazz();
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			visitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);
			v = new GraphVizOutputStream();
		}


	@Test
	public void TestSingleton2() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("sample/Singleton1");

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
			 // System.out.println(c.getName());
			  if(c.getName().equals("sample/Singleton1")){
				  assertEquals(true,c.getTags().contains("Singleton"));
			  }
		
		  }
	}

	@Test
	public void TestSingleton3() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("java/awt/Desktop");

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
			  System.out.println(c.getName());
			  if(c.getName().equals("java/awt/Desktop")){
				  assertEquals(false,c.getTags().contains("Singleton"));
			  }
		
		  }
	}

	@Test

	public void TestSingleton4() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("java/util/Calendar");

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
			  if(c.getName().equals("java/util/Calendar")){
				  assertEquals(false,c.getTags().contains("Singleton"));
			  }
		
		  }
	}

	
	


	@Test
	public void TestSingleton5() throws IOException {
		List<String> cs = new ArrayList<>();
		cs.add("java/io/FilterInputStream");

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
			  if(c.getName().equals("java/io/FilterInputStream")){
				  assertEquals(false,c.getTags().contains("Singleton"));
			  }
		
		  }
	}

}
