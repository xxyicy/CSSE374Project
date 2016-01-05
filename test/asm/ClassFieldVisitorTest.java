package asm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IField;
import impl.Clazz;

public class ClassFieldVisitorTest {

	private final String PRIVATE = "-";
	private final String DEFAULT = "";
	private IClass c;
	private ClassVisitor visitor;

	public ClassFieldVisitorTest() {

		c = new Clazz();
		visitor = new ClassFieldVisitor(Opcodes.ASM5, c);
	}

	@Test
	public void testVisit1() throws IOException {
		String className = "sample.Button";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IField> classField = c.getFields();
		IField field = classField.get(0);
		
		assertEquals("text",field.getName());
		assertEquals(DEFAULT,field.getAccess());
		assertEquals("java.lang.String",field.getType());
	}

	@Test
	public void testVisit2() throws IOException {
		String className = "sample.AbstractComponent";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		List<IField> classField = c.getFields();
		IField field = classField.get(1);
		
		assertEquals("components",field.getName());
		assertEquals(PRIVATE,field.getAccess());
		assertEquals("java.util.List",field.getType());
	}

}
