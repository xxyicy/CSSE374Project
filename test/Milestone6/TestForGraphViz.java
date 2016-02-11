package Milestone6;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IClass;
import api.IModel;
import app.Utility;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import impl.Clazz;
import impl.Model;
import pattern.api.IDetector;
import pattern.impl.DecoratorDetector;
import visitor.impl.GraphVizOutputStream;

public class TestForGraphViz {
	private IModel m;
	private IClass c;
	private ClassVisitor visitor;
	private GraphVizOutputStream v;
	private IDetector d;

	@Before
	public void setUp() throws Exception {
		m = new Model();
		c = new Clazz();
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
		visitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);
		v = new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));
		d = new DecoratorDetector();
	}

	@Test
	public void testAbstractSprite() throws Exception {
		String className = "problem.sprites.AbstractSprite";
		List<String> cs = new ArrayList<String>();
		cs.add(className);
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

			clazz = clazz.replaceAll("/", ".");
			if (!c.getName().contains("$") && !classRead.contains(clazz)) {
				m.addClass(c);
				classRead.add(clazz);
			}
		}

		m.setRelation(Utility.removeRelationNotInPackage(m));

		d.detect(m);
		v.start();
		v.write(m);
		v.end();

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

		expectedResult.append("AbstractSprite [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("color=\"blue\"\n");
		expectedResult
				.append("label = \"{AbstractSprite\\n\\<\\<abstract\\>\\>\\n|- dx : double\\l- dy: double\\1- shape:Shape\\1|- init() : void\\l+ getInstance() : Singleton1\\l}\"\n"
						+ "];\n");
		expectedResult.append("AbstractSprite -> AbstractSprite [arrowhead=\"vee\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
		
	}
	
	
}
