package Milestone4;

import static org.junit.Assert.*;

import java.io.IOException;


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
import visitor.impl.GraphVizOutputStream;

public class TestForGraphViz {

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
	public void testSingleton1() throws IOException {
		String className = "sample.Singleton1";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		
		v.Start();
		m.addClass(c);
		m.accept(v);
		v.end();
		
		System.out.println(v.toString());
		
		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("digraph G {\n");
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");
		expectedResult.append("node [\n");
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");	
		expectedResult.append("shape = \"record\"\n");	
		expectedResult.append("]\n");	
		expectedResult.append("edge [\n");	
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");	
		expectedResult.append("]\n");
		
		expectedResult.append("Singleton1 [\n");	
		expectedResult.append("shape=\"record\",\n");	
		expectedResult.append("color=\"blue\"\n");
		expectedResult.append("label = \"{Singleton1\\n\\<\\<Singleton\\>\\>\\n|- uniqueInstance : Singleton1\\l|- init() : void\\l+ getInstance() : Singleton1\\l}\"\n"
				+ "];\n");	
			expectedResult.append("Singleton1 -> Singleton1 [arrowhead=\"vee\"]\n");	
		expectedResult.append("}\n");
		
		assertEquals(expectedResult.toString(),v.toString());
		
	}

	@Test
	public void testSingleton2() throws IOException {
		String className = "sample.Singleton2";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		
		v.Start();
		m.addClass(c);
		m.accept(v);
		v.end();
		
		System.out.println(v.toString());
		
		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("digraph G {\n");
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");
		expectedResult.append("node [\n");
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");	
		expectedResult.append("shape = \"record\"\n");	
		expectedResult.append("]\n");	
		expectedResult.append("edge [\n");	
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");	
		expectedResult.append("]\n");
		
		expectedResult.append("Singleton2 [\n");	
		expectedResult.append("shape=\"record\",\n");	
		expectedResult.append("color=\"blue\"\n");
		expectedResult.append("label = \"{Singleton2\\n\\<\\<Singleton\\>\\>\\n|- uniqueInstance : Singleton2\\l|- init() : void\\l+ getInstance() : Singleton2\\l}\"\n"
				+ "];\n");	
			expectedResult.append("Singleton2 -> Singleton2 [arrowhead=\"vee\"]\n");	
		expectedResult.append("}\n");
		
		assertEquals(expectedResult.toString(),v.toString());
	}
	
	@Test
	public void testSingleton3() throws IOException {
		String className = "sample.Singleton3";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		
		v.Start();
		m.addClass(c);
		m.accept(v);
		v.end();
		
		System.out.println(v.toString());
		
		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("digraph G {\n");
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");
		expectedResult.append("node [\n");
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");	
		expectedResult.append("shape = \"record\"\n");	
		expectedResult.append("]\n");	
		expectedResult.append("edge [\n");	
		expectedResult.append("fontname = \"Avenir Book\"\n");
		expectedResult.append("fontsize = 10\n");	
		expectedResult.append("]\n");
		
		expectedResult.append("Singleton3 [\n");	
		expectedResult.append("shape=\"record\",\n");	
		expectedResult.append("color=\"blue\"\n");
		expectedResult.append("label = \"{Singleton3\\n\\<\\<Singleton\\>\\>\\n|- uniqueInstance : Singleton3\\l|- init() : void\\l+ getInstance() : Singleton3\\l}\"\n"
				+ "];\n");	
			expectedResult.append("Singleton3 -> Singleton3 [arrowhead=\"vee\"]\n");	
		expectedResult.append("}\n");
		
		assertEquals(expectedResult.toString(),v.toString());
	}

}
