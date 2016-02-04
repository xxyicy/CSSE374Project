package Milestone5;

import static org.junit.Assert.assertEquals;

import java.io.FileOutputStream;
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
import api.IRelation;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import impl.Clazz;
import impl.Model;
import pattern.api.IDetector;
import pattern.impl.AdapterDetector;
import pattern.impl.DecoratorDetector;
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
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
		visitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);
		v = new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));
	}

	@Test
	public void TestDecorator1() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java.io.InputStreamReader");

		IModel m = new Model();

		IDetector detect = new DecoratorDetector();
		List<String> classRead = new ArrayList<>();

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}

		}

		detect.detect(m);
		System.out.println(m.getPatterns());
		for (IClass c : m.getClasses()) {
			System.out.println(c.getName() + "blah");
			if (c.getName().equals("java/io/InputStreamReader")) {
				assertEquals(false, c.getTags().contains("decorator"));
			}

		}

	}

	@Test
	public void TestDecorator2() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java.io.OutputStreamWriter");

		IModel m = new Model();

		IDetector detect = new DecoratorDetector();
		List<String> classRead = new ArrayList<>();

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}

		}

		detect.detect(m);

		for (IClass c : m.getClasses()) {
			System.out.println(c.getName());
			if (c.getName().equals("java/io/OutputStreamWriter")) {

				assertEquals(false, c.getTags().contains("decorator"));
			}

			// if(c.getName().equals(""))

		}

	}

	@Test
	public void TestAdapter() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java.awt.event.MouseAdapter");

		IModel m = new Model();

		IDetector detect = new AdapterDetector();
		List<String> classRead = new ArrayList<>();

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}

		}

		detect.detect(m);
		System.out.println("patterns: " + m.getPatterns());
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName());
			if (c.getName().equals("java/awt/event/MouseAdapter")) {
				assertEquals(false, c.getTags().contains("adapter"));
			}

		}

		for (IRelation r : m.getRelations()) {
			System.out.println(r.getFrom());
			System.out.println(r.getTo());
			System.out.println(r.getType());
			// if(r.getFrom() && r.getTo() &&
			// r.getType().equals("association")){
			// r.getDes()
			// }
		}

	}

	@Test
	public void TestDecorator3() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("headfirst/decorator/starbuzz/Soy");

		IModel m = new Model();

		IDetector detect = new DecoratorDetector();
		List<String> classRead = new ArrayList<>();

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}

		}

		detect.detect(m);
		System.out.println("patterns: " + m.getPatterns());
		for (IClass c : m.getClasses()) {
			System.out.println(c.getName() + "  ->cc");
			if (c.getName().equals("headfirst/decorator/starbuzz/Soy")) {
				assertEquals(true, c.getTags().contains("decorator"));
			}
			if (c.getName().equals("headfirst/decorator/starbuzz/Beverage")) {
				assertEquals(true, c.getTags().contains("component"));
			}
		}

	}

	@Test
	public void TestDecorator4() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("headfirst/decorator/starbuzz/Milk");

		IModel m = new Model();

		IDetector detect = new DecoratorDetector();
		List<String> classRead = new ArrayList<>();

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}

		}

		detect.detect(m);
		System.out.println("patterns: " + m.getPatterns());
		for (IClass c : m.getClasses()) {
			System.out.println(c.getName() + "  ->cc");
			if (c.getName().equals("headfirst/decorator/starbuzz/Milk")) {
				assertEquals(true, c.getTags().contains("decorator"));
			}
			if (c.getName().equals("headfirst/decorator/starbuzz/Beverage")) {
				assertEquals(true, c.getTags().contains("component"));
			}
		}

	}

	@Test
	public void TestDecorator5() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/DecryptInput");

		IModel m = new Model();

		IDetector detect = new DecoratorDetector();
		List<String> classRead = new ArrayList<>();

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}

		}

		detect.detect(m);
		System.out.println("patterns: " + m.getPatterns());
		for (IClass c : m.getClasses()) {
			System.out.println(c.getName() + "  ->cc");
			if (c.getName().equals("problem/DecryptInput")) {
				assertEquals(true, c.getTags().contains("decorator"));
			}
			if (c.getName().equals("java/io/InputStream")) {
				assertEquals(true, c.getTags().contains("component"));
			}

		}

	}

	@Test
	public void TestAdapter2() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/client/IteratorToEnumerationAdapter");
		IModel m = new Model();

		IDetector detect = new AdapterDetector();
		List<String> classRead = new ArrayList<>();

		while (!cs.isEmpty()) {
			String clazz = cs.get(0);
			cs.remove(0);

			ClassReader reader = new ClassReader(clazz);
			IClass c = new Clazz();
			// make class declaration visitor to get superclass and interfaces
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m, cs);
			// DECORATE declaration visitor with field visitor
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
			// DECORATE field visitor with method visitor
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}

		}

		detect.detect(m);
		System.out.println("patterns: " + m.getPatterns());
		for (IClass c : m.getClasses()) {
		
			 System.out.println(c);
			if (c.getName().equals("problem/client/IteratorToEnumerationAdapter")) {
				
				assertEquals(true, c.getTags().contains("adapter"));
			}
			if (c.getName().equals("java/util/Enumeration")) {
				
				assertEquals(false, c.getTags().contains("adapter"));
			}
			if (c.getName().equals("java/util/Iterator")) {
				
				assertEquals(true, c.getTags().contains("adaptee"));
			}
			

		}

	}
	
}
