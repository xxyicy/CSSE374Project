package asm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

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
		visitor = new ClassFieldVisitor(Opcodes.ASM5, c);
	}

	@Test
	public void testVisit1() throws IOException {
		String className = "sample.Button";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IMethod> classMethod = c.getMethods();
		IMethod method = classMethod.get(0);
		
		assertEquals("setText",method.getName());
		assertEquals(PUBLIC,method.getAccess());
		assertEquals("void",method.getType());
		assertEquals("[java.lang.String]",method.getParamTypes());
		assertEquals("[Exception]",method.getExceptions());
	}

	@Test
	public void testVisit2() throws IOException {
		String className = "sample.LinuxButton";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IMethod> classMethod = c.getMethods();
		IMethod method = classMethod.get(0);
		
		assertEquals("drawComponent",method.getName());
		assertEquals(PUBLIC,method.getAccess());
		assertEquals("void",method.getType());
		assertEquals("[Graphics]",method.getParamTypes());
		assertEquals("",method.getExceptions());
	}

}
