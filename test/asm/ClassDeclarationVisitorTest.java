package asm;

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
import api.IDeclaration;
import api.IModel;
import api.IRelation;
import impl.Model;
import impl.Clazz;


public class ClassDeclarationVisitorTest {
	private final String INTERFACE = "interface";
	private final String ABSTRACT = "abstract";
	private final String CLASS = "class";
	private IModel m;
	private IClass c;
	
	private ClassVisitor visitor;

	public ClassDeclarationVisitorTest() throws IOException {
		this.m = new Model();
		this.c = new Clazz();
		visitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m);
	}

	@Test
	public void testClassNameAndType() throws IOException {
		String className = "sample.IComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		IDeclaration classDeclaration = c.getDeclaration();
		
		assertEquals(INTERFACE,classDeclaration.getType());
		assertEquals("sample/IComponent", classDeclaration.getName());
	}
	
	@Test
	public void testClassNameAndType2() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		IDeclaration classDeclaration = c.getDeclaration();
		
		assertEquals(ABSTRACT,classDeclaration.getType());
		assertEquals("sample/AbstractComponent", classDeclaration.getName());
	}
	
	@Test
	public void testClassNameAndType3() throws IOException {
		String className = "sample.LinuxButton";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		IDeclaration classDeclaration = c.getDeclaration();
		
		assertEquals(CLASS,classDeclaration.getType());
		assertEquals("sample/LinuxButton", classDeclaration.getName());
	}
	
	@Test
	public void tsetInterfaces1() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<IRelation> relations = m.getRelations();
		Set<String> interfaceNames = new HashSet<String>();
		for (IRelation r: relations){
			if (r.getType().equals("implements")){
				interfaceNames.add(r.getTo());
			}
		}
		assertTrue(interfaceNames.contains("sample/IComponent"));
	}
	
	@Test
	public void testSuperClass() throws IOException {
		String className = "sample.Button";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<IRelation> relations = m.getRelations();
		String superClassName = "";
		for (IRelation r: relations){
			if (r.getType().equals("extends")){
				superClassName = r.getTo();
			}
		}
		assertTrue(superClassName.equals("sample/AbstractComponent"));
	}
	
}
