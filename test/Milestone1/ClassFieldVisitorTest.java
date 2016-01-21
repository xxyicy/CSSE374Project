package Milestone1;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IField;
import api.IModel;
import api.IRelation;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import impl.Clazz;
import impl.Model;

public class ClassFieldVisitorTest {

	private final String PRIVATE = "-";
	private final String DEFAULT = "";
	private final String PUBLIC = "+";
	private IModel m;
	private IClass c;
	private ClassVisitor visitor;

	public ClassFieldVisitorTest() {
		this.m = new Model();
		this.c = new Clazz();
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,c,m);
		visitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
	}

	@Test
	public void testMethodNameAndAccess() throws IOException {
		String className = "sample.Button";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IField> classField = c.getFields();
		System.out.println(classField);
		IField field = classField.get(0);

		assertEquals("text", field.getName());
		assertEquals(DEFAULT, field.getAccess());
		assertEquals("java.lang.String", field.getType());
	}

	@Test
	public void testMethodNameAndAccess2() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IField> classField = c.getFields();
		IField field = classField.get(1);

		assertEquals("components", field.getName());
		assertEquals(PRIVATE, field.getAccess());
		assertEquals("java.util.List", field.getType());
	}
	
	@Test 
	public void testMethodNameAndAccess3() throws IOException {
		String className = "sample.TestClass2";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IField> classField = c.getFields();
		IField field = classField.get(1);

		assertEquals("count", field.getName());
		assertEquals(PUBLIC, field.getAccess());
		assertEquals("int", field.getType());
	}

	// test association
	@Test
	public void tsetAssociation1() throws IOException {
		String className = "sample.TestClass2";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<IRelation> relations = m.getRelations();
		Set<String> associationNames = new HashSet<String>();
		for (IRelation r: relations){
			if (r.getType().equals("association")){
				associationNames.add(r.getTo());
			}
		}
		assertTrue(associationNames.contains("sample.TestClass1"));
		assertTrue(associationNames.contains("sample/Button"));
		assertTrue(associationNames.contains("int"));
		
	}

	// test association
	@Test
	public void tsetAssociation2() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		Set<IRelation> relations = m.getRelations();
		Set<String> associationNames = new HashSet<String>();
		for (IRelation r: relations){
			if (r.getType().equals("association")){
				associationNames.add(r.getTo());
			}
		}
		assertTrue(associationNames.contains("java.awt.Rectangle"));
		assertTrue(associationNames.contains("sample.IComponent"));
	}

}
