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
import app.ClassFinder;
import app.Utility;
import asm.ClassDeclarationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import impl.Clazz;
import impl.Model;
import pattern.api.IDetector;
import pattern.impl.CompositeDetector;
import visitor.impl.GraphVizOutputStream;

public class TestForGraphViz {
	private IModel m;
	private IClass c;
	private GraphVizOutputStream v;
	private IDetector d;

	@Before
	public void setUp() throws Exception {
		m = new Model();
		c = new Clazz();
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, c, m);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, c, m);
		new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, c, m);
		v = new GraphVizOutputStream(new FileOutputStream("./output/output.txt"));

		d = new CompositeDetector();

	}

	@Test
	public void testAbstractSprite() throws Exception {
		Utility.APP_TYPE = Utility.APP_UML;
		List<Class<?>> classes = ClassFinder.find("sprites");
		List<String> cs = new ArrayList<>();
		for (Class<?> clazz : classes) {
			cs.add(clazz.getName());
		}
		
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

		System.out.println(m);
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
		expectedResult.append("ISprite [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("style=\"filled\"\n");
		expectedResult.append("fillcolor=\"yellow\"\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\nISprite\\n\\<\\<Component\\>\\>\\n|+ move(arg0:Dimension) : void\\l+ getShape() : Shape\\l+ add(arg0:ISprite) : void\\l+ remove(arg0:ISprite) : void\\l+ getChild(arg0:int) : ISprite\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("RectangleTower [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("style=\"filled\"\n");
		expectedResult.append("fillcolor=\"yellow\"\n");
		expectedResult.append("label = \"{RectangleTower\\n\\<\\<Leaf\\>\\>\\n|+ init(arg0:double,arg1:double,arg2:double,arg3:double) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("NullSpriteIterator [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{NullSpriteIterator|+ init() : void\\l+ hasNext() : boolean\\l+ next() : ISprite\\l+ remove() : void\\l+ next() : Object\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("CompositeSpriteIterator [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{CompositeSpriteIterator|- stack : Stack\\l|+ init(arg0:Iterator) : void\\l+ hasNext() : boolean\\l+ next() : ISprite\\l+ remove() : void\\l+ next() : Object\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("CompositeSprite [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("style=\"filled\"\n");
		expectedResult.append("fillcolor=\"yellow\"\n");
		expectedResult.append("label = \"{CompositeSprite\\n\\<\\<Composite\\>\\>\\n| children : List\\l|+ init(arg0:double,arg1:double,arg2:double,arg3:double) : void\\l+ iterator() : Iterator\\l+ add(arg0:ISprite) : void\\l+ remove(arg0:ISprite) : void\\l+ getChild(arg0:int) : ISprite\\l+ move(arg0:Dimension) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("CircleSprite [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("style=\"filled\"\n");
		expectedResult.append("fillcolor=\"yellow\"\n");
		expectedResult.append("label = \"{CircleSprite\\n\\<\\<Leaf\\>\\>\\n|+ init(arg0:double,arg1:double,arg2:double,arg3:double) : void\\l+ move(arg0:Dimension) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Iterator [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\nIterator|+ hasNext() : boolean\\l+ next() : Object\\l+ remove() : void\\l+ forEachRemaining(arg0:Consumer) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("RectangleSprite [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("style=\"filled\"\n");
		expectedResult.append("fillcolor=\"yellow\"\n");
		expectedResult.append("label = \"{RectangleSprite\\n\\<\\<Leaf\\>\\>\\n|+ init(arg0:double,arg1:double,arg2:double,arg3:double) : void\\l+ move(arg0:Dimension) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("SpriteFactory [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{SpriteFactory|+ DX : double\\l+ DY : double\\l+ WIDTH : double\\l+ HEIGHT : double\\l- random : Random\\l- sprites : List\\l| clinit() : void\\l+ init() : void\\l+ computeRandomLocation(arg0:Dimension) : Point2D\\l+ createRandomSprite(arg0:Dimension) : ISprite throws Exception\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("CrystalBall [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("style=\"filled\"\n");
		expectedResult.append("fillcolor=\"yellow\"\n");
		expectedResult.append("label = \"{CrystalBall\\n\\<\\<Leaf\\>\\>\\n|+ init(arg0:double,arg1:double,arg2:double,arg3:double) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Iterable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\nIterable|+ iterator() : Iterator\\l+ forEach(arg0:Consumer) : void\\l+ spliterator() : Spliterator\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("AbstractSprite [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("style=\"filled\"\n");
		expectedResult.append("fillcolor=\"yellow\"\n");
		expectedResult.append("label = \"{AbstractSprite\\n\\<\\<Component\\>\\>\\n|# dx : double\\l# dy : double\\l# shape : Shape\\l|+ init(arg0:double,arg1:double,arg2:double,arg3:double) : void\\l# computeNewBoundsAfterMoving(arg0:Dimension) : Rectangle2D\\l+ getShape() : Shape\\l+ add(arg0:ISprite) : void\\l+ remove(arg0:ISprite) : void\\l+ getChild(arg0:int) : ISprite\\l+ iterator() : Iterator\\l+ move(arg0:Dimension) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("AbstractSprite -> NullSpriteIterator [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("NullSpriteIterator -> Iterator [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("CompositeSprite -> ISprite [arrowhead=\"vee\"]\n");
		expectedResult.append("CompositeSpriteIterator -> Iterator [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("RectangleTower -> CompositeSprite [arrowhead=\"onormal\"]\n");
		expectedResult.append("Iterable -> Iterator [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("ISprite -> Iterable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("AbstractSprite -> Iterator [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("CompositeSpriteIterator -> ISprite [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("CrystalBall -> RectangleTower [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("AbstractSprite -> ISprite [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("CompositeSprite -> Iterator [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("CompositeSpriteIterator -> Iterator [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("RectangleSprite -> AbstractSprite [arrowhead=\"onormal\"]\n");
		expectedResult.append("CrystalBall -> CircleSprite [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("CrystalBall -> CompositeSprite [arrowhead=\"onormal\"]\n");
		expectedResult.append("RectangleTower -> RectangleSprite [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("CompositeSprite -> AbstractSprite [arrowhead=\"onormal\"]\n");
		expectedResult.append("AbstractSprite -> ISprite [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("SpriteFactory -> ISprite [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("CircleSprite -> AbstractSprite [arrowhead=\"onormal\"]\n");
		expectedResult.append("NullSpriteIterator -> ISprite [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("ISprite -> ISprite [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("CompositeSprite -> CompositeSpriteIterator [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
		
	}
	
	
}
