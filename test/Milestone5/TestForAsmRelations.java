package Milestone5;

import static org.junit.Assert.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
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

public class TestForAsmRelations {
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
	public void TestAdapterRelation() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("problem/client/IteratorToEnumerationAdapter");

		IModel m = new Model();

		IDetector detect = new AdapterDetector(0);
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

		for (IRelation r : m.getRelations()) {
//			 System.out.println(r.getFrom()+" -->cc");
//			 System.out.println(r.getTo()+" -->ad");
//			 System.out.println(r.getType()+" -->wa");
//			 System.out.println(r.getDes()+" -->ws");
			if (r.getFrom().equals("problem/client/IteratorToEnumerationAdapter")
					&& r.getTo().equals("java/util/Iterator") && r.getType().equals("association")) {
				assertEquals(true, r.getDes().equals("adapts"));
			}
		}

	}

	@Test
	public void TestDecoratorRelation() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("headfirst/decorator/starbuzz/Soy");

		IModel m = new Model();

		IDetector detect = new DecoratorDetector(0);
		
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
		
		for (IRelation r : m.getRelations()) {
//			System.out.println(r.getFrom() + " -->cc");
//			System.out.println(r.getTo() + " -->ad");
//			System.out.println(r.getType() + " -->wa");
//			System.out.println(r.getDes() + " -->Relations");
			if (r.getFrom().equals("headfirst/decorator/starbuzz/Soy")
					&& r.getTo().equals("headfirst.decorator.starbuzz.Beverage") && r.getType().equals("association")) {
				assertEquals(true, r.getDes().equals("decorates"));
			}

		}

	}
	
	
	@Test
	public void TestDecorator2Relation() throws Exception {
		List<String> cs = new ArrayList<>();
		cs.add("headfirst/decorator/starbuzz/Milk");

		IModel m = new Model();

		IDetector detect = new DecoratorDetector(0);
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

		for (IRelation r : m.getRelations()) {
			System.out.println(r.getFrom() + " -->cc");
			System.out.println(r.getTo() + " -->ad");
			System.out.println(r.getType() + " -->wa");
			System.out.println(r.getDes() + " -->Relations");
			if (r.getFrom().equals("headfirst/decorator/starbuzz/Milk")
					&& r.getTo().equals("headfirst.decorator.starbuzz.Beverage") && r.getType().equals("association")) {
				assertEquals(true, r.getDes().equals("decorates"));
			}

		}

	}
}
