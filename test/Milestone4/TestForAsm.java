package Milestone4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IModel;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import impl.Clazz;
import impl.Model;
import pattern.api.IDetector;
import pattern.impl.SingletonDetector;

public class TestForAsm {

//	private IModel m;
//	private IClass c;
	@Before
	public void setUp() throws Exception {
//		m = new Model();
//		c = new Clazz();
//		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m);
//		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
//		new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);
//		new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));
	}

	@Test
	public void TestSingleton1() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java.lang.Runtime");

		IModel m = new Model();

		IDetector detect = new SingletonDetector();
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
			// System.out.println(c.getName());
			if (c.getName().equals("java/lang/Runtime")) {
				assertEquals(true, c.getTags().contains("Singleton"));
			}

		}

	}

	@Test
	public void TestSingleton2() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("sample.Singleton1");

		IModel m = new Model();

		IDetector detect = new SingletonDetector();
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
			// System.out.println(c.getName());
			if (c.getName().equals("sample/Singleton1")) {
				assertEquals(true, c.getTags().contains("Singleton"));
			}

		}
	}

	@Test
	public void TestSingleton3() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java.awt.Desktop");

		IModel m = new Model();

		IDetector detect = new SingletonDetector();
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
			if (c.getName().equals("java/awt/Desktop")) {
				assertEquals(false, c.getTags().contains("Singleton"));
			}

		}
	}

	@Test

	public void TestSingleton4() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java.util.Calendar");

		IModel m = new Model();

		IDetector detect = new SingletonDetector();
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
			if (c.getName().equals("java/util/Calendar")) {
				assertEquals(false, c.getTags().contains("Singleton"));
			}

		}
	}

	@Test
	public void TestSingleton5() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java.io.FilterInputStream");

		IModel m = new Model();

		IDetector detect = new SingletonDetector();
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
			if (c.getName().equals("java/io/FilterInputStream")) {
				assertEquals(false, c.getTags().contains("Singleton"));
			}

		}
	}

	@Test
	public void TestSingleton6() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("sample.Singleton2");

		IModel m = new Model();

		IDetector detect = new SingletonDetector();
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
			if (c.getName().equals("sample/Singleton2")) {
				assertEquals(true, c.getTags().contains("Singleton"));
			}

		}
	}

}
