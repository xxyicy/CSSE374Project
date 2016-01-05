package asm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IDeclaration;
import impl.Clazz;


public class ClassDeclarationVisitorTest {
	private final String INTERFACE = "interface";
	private final String ABSTRACT = "abstract";
	private final String CLASS = "class";
	private IClass c;
	private ClassVisitor visitor;

	public ClassDeclarationVisitorTest() throws IOException {
		
	c = new Clazz();
		visitor = new ClassDeclarationVisitor(Opcodes.ASM5, c);
	}

	@Test
	public void testVisit1() throws IOException {
		String className = "sample.IComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		IDeclaration classDeclaration = c.getDeclaration();
		
		List<String> nameOfInterfaces = classDeclaration.getInterfaces();
		
		assertEquals(INTERFACE,classDeclaration.getType());
		assertEquals("IComponent", classDeclaration.getName());
		assertEquals("Object", classDeclaration.getSuper());
		assertEquals(0, nameOfInterfaces.size());
	}
	
	@Test
	public void testVisit2() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		IDeclaration classDeclaration = c.getDeclaration();
		
		List<String> nameOfInterfaces = classDeclaration.getInterfaces();
		
		assertEquals(ABSTRACT,classDeclaration.getType());
		assertEquals("AbstractComponent", classDeclaration.getName());
		assertEquals("Object", classDeclaration.getSuper());
		assertEquals(1, nameOfInterfaces.size());
		assertEquals("IComponent",nameOfInterfaces.get(0));
	}
	
	@Test
	public void testVisit3() throws IOException {
		String className = "sample.LinuxButton";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		IDeclaration classDeclaration = c.getDeclaration();
		
		List<String> nameOfInterfaces = classDeclaration.getInterfaces();
		
		assertEquals(CLASS,classDeclaration.getType());
		assertEquals("LinuxButton", classDeclaration.getName());
		assertEquals("Button", classDeclaration.getSuper());
		assertEquals(0, nameOfInterfaces.size());
	}
	
}
