package asm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IMethod;
import impl.Clazz;

public class ClassMethodVisitorTest {

	private final String PRIVATE = "-";
	private final String PUBLIC = "+";
	private final String PROTECTED = "#";
	private final String DEFAULT = "";
	private IClass c;
	private ClassVisitor visitor;

	public ClassMethodVisitorTest() {
		c = new Clazz();
		visitor = new ClassMethodVisitor(Opcodes.ASM5, c);
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
		assertEquals("void", method.getType());
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
		assertEquals("void", method.getType());
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
		assertEquals("void", method.getType());
		assertEquals(0, method.getParamTypes().size());
		assertEquals(0, method.getExceptions().size());
	}

	// test use
	@Test
	public void tsetVisit4() throws IOException {
		String className = "sample.TestClass2";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<String> useSet = c.getUses();
		Iterator<String> t = useSet.iterator();
		
		assertEquals("java/lang/Object", t.next());
		assertEquals("sample/LinuxButton", t.next());
		assertEquals("java/awt/Rectangle", t.next());
	}

	// test use
	@Test
	public void tsetVisit5() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<String> useSet = c.getUses();
		Iterator<String> t = useSet.iterator();

		assertEquals("java/lang/Object", t.next());
		assertEquals("sample/AbstractComponent", t.next());
		assertEquals("java/util/ArrayList", t.next());

	}
}
