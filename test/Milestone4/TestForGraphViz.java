package Milestone4;

import static org.junit.Assert.*;

import java.io.FileOutputStream;

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
import pattern.impl.SingletonDetector;
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
		d = new SingletonDetector(0);
	}

	@Test
	public void testSingleton1() throws Exception {
		String className = "sample.Singleton1";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);

		m.addClass(c);
		d.detect(m);
		m.setRelation(Utility.removeRelationNotInPackage(m));
		v.start();
		v.write(m);
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
		expectedResult
				.append("label = \"{Singleton1\\n\\<\\<Singleton\\>\\>\\n|- uniqueInstance : Singleton1\\l|- init() : void\\l+ getInstance() : Singleton1\\l}\"\n"
						+ "];\n");
		expectedResult.append("Singleton1 -> Singleton1 [arrowhead=\"vee\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());

	}

	@Test
	public void testSingleton2() throws Exception {
		String className = "sample.Singleton2";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);

		m.addClass(c);
		d.detect(m);
		m.setRelation(Utility.removeRelationNotInPackage(m));
		v.start();
		v.write(m);
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
		expectedResult
				.append("label = \"{Singleton2\\n\\<\\<Singleton\\>\\>\\n|- uniqueInstance : Singleton2\\l|- init() : void\\l+ getInstance() : Singleton2\\l}\"\n"
						+ "];\n");
		expectedResult.append("Singleton2 -> Singleton2 [arrowhead=\"vee\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
	}

	@Test
	public void testSingleton3() throws Exception {
		String className = "sample.Singleton3";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);

		m.addClass(c);
		d.detect(m);
		m.setRelation(Utility.removeRelationNotInPackage(m));
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

		expectedResult.append("Singleton3 [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("color=\"blue\"\n");
		expectedResult
				.append("label = \"{Singleton3\\n\\<\\<Singleton\\>\\>\\n|- uniqueInstance : Singleton3\\l|- init() : void\\l+ getInstance() : Singleton3\\l}\"\n"
						+ "];\n");
		expectedResult.append("Singleton3 -> Singleton3 [arrowhead=\"vee\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
	}

	@Test
	public void testSingleton4() throws Exception {
		String className = "java.lang.Runtime";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);

		m.addClass(c);
		d.detect(m);
		m.setRelation(Utility.removeRelationNotInPackage(m));
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

		expectedResult.append("Runtime [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("color=\"blue\"\n");
		expectedResult.append("label = \"{Runtime\\n\\<\\<Singleton\\>\\>\\n|- " + "currentRuntime : Runtime\\l|+ "
				+ "getRuntime() : Runtime\\l- init() : void\\l+ exit(arg0:int) : void\\l+ "
				+ "addShutdownHook(arg0:Thread) : void\\l+ removeShutdownHook(arg0:Thread) : boolean\\l+ "
				+ "halt(arg0:int) : void\\l+ runFinalizersOnExit(arg0:boolean) : void\\l+ "
				+ "exec(arg0:String) : Process throws IOException\\l+ exec(arg0:String,arg1:String[]) : Process throws IOException\\l+ "
				+ "exec(arg0:String,arg1:String[],arg2:File) : Process throws IOException\\l+ "
				+ "exec(arg0:String[]) : Process throws IOException\\l+ "
				+ "exec(arg0:String[],arg1:String[]) : Process throws IOException\\l+ "
				+ "exec(arg0:String[],arg1:String[],arg2:File) : Process throws IOException\\l+ "
				+ "availableProcessors() : int\\l+ freeMemory() : long\\l+ totalMemory() : long\\l+ maxMemory() : long\\l+ "
				+ "gc() : void\\l- runFinalization0() : void\\l+ runFinalization() : void\\l+ "
				+ "traceInstructions(arg0:boolean) : void\\l+ traceMethodCalls(arg0:boolean) : void\\l+ "
				+ "load(arg0:String) : void\\l load0(arg0:Class,arg1:String) : void\\l+ loadLibrary(arg0:String) : void\\l "
				+ "loadLibrary0(arg0:Class,arg1:String) : void\\l+ "
				+ "getLocalizedInputStream(arg0:InputStream) : InputStream\\l+ "
				+ "getLocalizedOutputStream(arg0:OutputStream) : OutputStream\\l clinit() : void\\l}\"\n" + "];\n");
		expectedResult.append("Runtime -> Runtime [arrowhead=\"vee\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
	}

	@Test
	public void testSingleton5() throws Exception {
		String className = "java.awt.Desktop";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);

		m.addClass(c);
		d.detect(m);
		m.setRelation(Utility.removeRelationNotInPackage(m));
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

		expectedResult.append("Desktop [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{Desktop|- peer : DesktopPeer\\l|- "
				+ "init() : void\\l+ getDesktop() : Desktop\\l+ isDesktopSupported() : boolean\\l+ "
				+ "isSupported(arg0:Desktop$Action) : boolean\\l- checkFileValidation(arg0:File) : void\\l- "
				+ "checkActionSupport(arg0:Desktop$Action) : void\\l- checkAWTPermission() : void\\l+ "
				+ "open(arg0:File) : void throws IOException\\l+ edit(arg0:File) : void throws IOException\\l+ "
				+ "print(arg0:File) : void throws IOException\\l+ browse(arg0:URI) : void throws IOException\\l+ "
				+ "mail() : void throws IOException\\l+ mail(arg0:URI) : void throws IOException\\l- "
				+ "checkExec() : void throws SecurityException\\l}\"\n" + "];\n");
		expectedResult.append("Desktop -> Desktop [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
	}

	@Test
	public void testSingleton6() throws Exception {
		String className = "java.io.FilterInputStream";
		ClassReader reader = new ClassReader(className);
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);

		m.addClass(c);
		d.detect(m);
		m.setRelation(Utility.removeRelationNotInPackage(m));
		v.start();
		v.write(m);
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

		expectedResult.append("FilterInputStream [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult
				.append("label = \"{FilterInputStream|# in : " + "InputStream\\l|# init(arg0:InputStream) : void\\l+ "
						+ "read() : int throws IOException\\l+ read(arg0:byte[]) : int throws IOException\\l+ "
						+ "read(arg0:byte[],arg1:int,arg2:int) : int throws IOException\\l+ skip(arg0:long) : long throws IOException\\l+ "
						+ "available() : int throws IOException\\l+ close() : void throws IOException\\l+ mark(arg0:int) : void\\l+ "
						+ "reset() : void throws IOException\\l+ markSupported() : boolean\\l}\"\n" + "];\n");
//		expectedResult.append("FilterInputStream -> long [arrowhead=\"vee\",style=\"dashed\"]\n");
//		expectedResult.append("FilterInputStream -> byte[] [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("}\n");
		assertEquals(expectedResult.toString(), v.toString());
	}

}
