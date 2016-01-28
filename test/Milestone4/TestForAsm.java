
package Milestone4;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IMethod;
import api.IModel;
import app.ClassFinder;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import asm.SequenceMethodVisitor;
import impl.Clazz;
import impl.Method;
import impl.Model;
import visitor.api.ISDVisitor;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.SDEditOutputStream;

public class TestForAsm {
	
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
			v = new GraphVizOutputStream();
		}

	@Test
	public void TestSingleton() throws IOException {
		String className = "sample.Singleton1";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertEquals(true, c.getDeclaration().isSingleton());

	}

	@Test
	public void Test2() throws IOException {
		String className1 = "java.lang.Runtime";
		ClassReader reader1 = new ClassReader(className1);
		reader1.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertEquals(true, c.getDeclaration().isSingleton());
	}

	@Test
	public void Test3() throws IOException {
		String className1 = "java.awt.Desktop";
		ClassReader reader1 = new ClassReader(className1);
		reader1.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertEquals(false, c.getDeclaration().isSingleton());
	}
	


	@Test
	public void Test4() throws IOException {
		String className1 = "java.util.Calendar";
		ClassReader reader1 = new ClassReader(className1);
		reader1.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertEquals(false, c.getDeclaration().isSingleton());
	}

	@Test
	public void Test5() throws IOException {
		String className1 = "java.io.FilterInputStream";
		ClassReader reader1 = new ClassReader(className1);
		reader1.accept(visitor, ClassReader.EXPAND_FRAMES);
		assertEquals(false, c.getDeclaration().isSingleton());
	}

}
