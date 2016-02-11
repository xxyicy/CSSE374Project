package Milestone6;

import static org.junit.Assert.assertEquals;

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
import pattern.impl.CompositeDetector;

public class TestForAsm {
	private IModel m;
	private IClass c;
	
	@Before
	public void setUp() throws Exception {
		m = new Model();
		c = new Clazz();
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
		new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);
	}

	@Test
	public void TestComposite1() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/sprites/CompositeSprite");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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

		System.out.println(m);
		detect.detect(m);
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "baa");

			if (c.getName().equals("problem/sprites/CompositeSprite")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(true, c.getTags().contains("Composite"));
				// assertEquals(true,c.getFields().size()!=0);
			}

		}

	}

	@Test
	public void TestComposite2() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("java/lang/Iterable");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "baa");

			if (c.getName().equals("java/lang/Iterable")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(false, c.getTags().contains("composite"));
				assertEquals(false, c.getFields().size() != 0);
			}
			// System.out.println(c.getFields());

			// if(c.getTags().equals(o))

		}

	}

	@Test
	public void TestComposite3() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/sprites/CrystalBall");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "baa");

			if (c.getName().equals("problem/sprites/CrystalBall")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(false, c.getTags().contains("Composite"));

			}

		}

	}

	@Test
	public void TestComposite4() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/sprites/ISprite");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "baa");

			if (c.getName().equals("problem/sprites/ISprite")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(false, c.getTags().contains("Composite"));
				assertEquals(true, c.getFields().size() == 0);
				// assertEquals(true, );

				// assertEquals(false,c.getFields().size()>1);
			}

		}

	}

	@Test
	public void TestComposite5() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/sprites/NoLongerComposite");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "baa");

			if (c.getName().equals("problem/sprites/NoLongerComposite")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(false, c.getTags().contains("Composite"));

			}

		}

	}

	@Test
	public void TestComposite6() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/sprites/NoLongerComposite2");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "baa");

			if (c.getName().equals("problem/sprites/NoLongerComposite2")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(false, c.getTags().contains("Composite"));

			}

		}

	}

	@Test
	public void TestComposite7() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("CompositeExample/Details");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "blah");

			if (c.getName().equals("CompositeExample/Details")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(true, c.getTags().contains("Composite"));

			}

		}

	}

	@Test
	public void TestComposite8() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("CompositeExample/CompositeExample");

		IModel m = new Model();

		IDetector detect = new CompositeDetector();
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
		System.out.println(m);
		for (IClass c : m.getClasses()) {
			// System.out.println(c.getName()+"boo");
			System.out.println(c.getFields() + "blah");

			if (c.getName().equals("CompositeExample/CompositeExample")) {
				// System.out.println(c.getName() + "blah");
				assertEquals(false, c.getTags().contains("Composite"));

			}

		}

	}

}
