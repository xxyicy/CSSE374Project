package Milestone1;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IMethod;
import api.IModel;
import api.IRelation;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import impl.Model;
import impl.Clazz;

public class ClassMethodVisitorTest {

	private final String PRIVATE = "-";
	private final String PUBLIC = "+";
	private final String DEFAULT = "";
	private IModel m;
	private IClass c;
	private ClassVisitor visitor;

	public ClassMethodVisitorTest() {
		m = new Model();
		c = new Clazz();
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,c,m);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor,c,m);
		visitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c,m);
	}

	@Test
	public void testVisit1() throws IOException {
		String className = "sample.Button";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IMethod> classMethod = c.getMethods();
		IMethod method = classMethod.get(3);

		assertEquals("setText", method.getName());
		assertEquals(PRIVATE, method.getAccess());
		assertEquals("void", method.getReturnType());
		assertEquals("java.lang.String", method.getParamTypes().get(0));
		assertEquals("java/lang/Exception", method.getExceptions().get(0));
	}

	@Test
	public void testVisit2() throws IOException {
		String className = "sample.LinuxButton";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IMethod> classMethod = c.getMethods();
		IMethod method = classMethod.get(1);

		assertEquals("drawComponent", method.getName());
		assertEquals(PUBLIC, method.getAccess());
		assertEquals("void", method.getReturnType());
		assertEquals("java.awt.Graphics2D", method.getParamTypes().get(0));
		assertEquals(0, method.getExceptions().size());
	}

	@Test
	public void testVisit3() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IMethod> classMethod = c.getMethods();
		IMethod method = classMethod.get(0);

		assertEquals("init", method.getName());
		assertEquals(DEFAULT, method.getAccess());
		assertEquals("void", method.getReturnType());
		assertEquals(0, method.getParamTypes().size());
		assertEquals(0, method.getExceptions().size());
	}

	// test use
	@Test
	public void tsetUse1() throws IOException {
		String className = "sample.TestClass2";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<IRelation> relations = m.getRelations();
		Set<String> useNames = new HashSet<String>();
		for (IRelation r: relations){
			if (r.getType().equals("use")){
//				System.out.println(r.getTo());
				useNames.add(r.getTo());
			}
		}
		assertTrue(useNames.contains("sample.AbstractComponent"));
		assertTrue(useNames.contains("sample/LinuxButton"));
	}

	// test use
	@Test
	public void tsetUse2() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<IRelation> relations = m.getRelations();
		Set<String> useNames = new HashSet<String>();
		for (IRelation r: relations){
			if (r.getType().equals("use")){
				System.out.println(r.getTo());
				useNames.add(r.getTo());
			}
		}
		assertTrue(useNames.contains("java.util.List"));
		assertTrue(useNames.contains("java.awt.Graphics2D"));
		assertTrue(useNames.contains("boolean"));
		assertTrue(useNames.contains("sample/AbstractComponent"));

	}
}
