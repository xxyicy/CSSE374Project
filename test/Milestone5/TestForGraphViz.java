package Milestone5;

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
	public void testInputStreamReader() throws Exception {
		String className = "java.io.InputStreamReader";
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
		expectedResult.append("Readable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\n"
				+ "Readable|+ read(arg0:CharBuffer) : int throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Reader [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{Reader|# lock : Object\\l"
				+ "- maxSkipBufferSize : int\\l- skipBuffer : char[]\\l|# init() : void\\l# "
				+ "init(arg0:Object) : void\\l+ read(arg0:CharBuffer) : int throws IOException\\l+ read() : "
				+ "int throws IOException\\l+ read(arg0:char[]) : int throws IOException\\l+ "
				+ "read(arg0:char[],arg1:int,arg2:int) : int throws IOException"
				+ "\\l+ skip(arg0:long) : long throws IOException\\l+ ready() : "
				+ "boolean throws IOException\\l+ markSupported() : boolean\\l+ mark(arg0:int) : void throws IOException\\l+ "
				+ "reset() : void throws IOException\\l+ close() : void throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("InputStreamReader [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult
				.append("label = \"{InputStreamReader|- sd : StreamDecoder\\l|+ init(arg0:InputStream) : void\\l+ "
						+ "init(arg0:InputStream,arg1:String) : void throws UnsupportedEncodingException\\l+ "
						+ "init(arg0:InputStream,arg1:Charset) : void\\l+ init(arg0:InputStream,arg1:CharsetDecoder) : void\\l+ "
						+ "getEncoding() : String\\l+ read() : "
						+ "int throws IOException\\l+ read(arg0:char[],arg1:int,arg2:int) : "
						+ "int throws IOException\\l+ ready() : boolean throws IOException\\l+ close() : void throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Closeable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult
				.append("label = \"{\\<\\<interface\\>\\>\\nCloseable|+ close() : void throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("AutoCloseable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult
				.append("label = \"{\\<\\<interface\\>\\>\\nAutoCloseable|+ close() : void throws Exception\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Reader -> Closeable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("Closeable -> AutoCloseable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("Reader -> Readable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("InputStreamReader -> Reader [arrowhead=\"onormal\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
	}

	@Test
	public void testOutputStreamWriter() throws Exception {
		String className = "java.io.OutputStreamWriter";
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
		expectedResult.append("Writer [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append(
				"label = \"{Writer|- writeBuffer : char[]\\l- WRITE_BUFFER_SIZE : int\\l# lock : Object\\l|# init() : void\\l# init(arg0:Object) : void\\l+ write(arg0:int) : void throws IOException\\l+ write(arg0:char[]) : void throws IOException\\l+ write(arg0:char[],arg1:int,arg2:int) : void throws IOException\\l+ write(arg0:String) : void throws IOException\\l+ write(arg0:String,arg1:int,arg2:int) : void throws IOException\\l+ append(arg0:CharSequence) : Writer throws IOException\\l+ append(arg0:CharSequence,arg1:int,arg2:int) : Writer throws IOException\\l+ append(arg0:char) : Writer throws IOException\\l+ flush() : void throws IOException\\l+ close() : void throws IOException\\l+ append(arg0:char) : Appendable throws IOException\\l+ append(arg0:CharSequence,arg1:int,arg2:int) : Appendable throws IOException\\l+ append(arg0:CharSequence) : Appendable throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("OutputStreamWriter [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append(
				"label = \"{OutputStreamWriter|- se : StreamEncoder\\l|+ init(arg0:OutputStream,arg1:String) : void throws UnsupportedEncodingException\\l+ init(arg0:OutputStream) : void\\l+ init(arg0:OutputStream,arg1:Charset) : void\\l+ init(arg0:OutputStream,arg1:CharsetEncoder) : void\\l+ getEncoding() : String\\l flushBuffer() : void throws IOException\\l+ write(arg0:int) : void throws IOException\\l+ write(arg0:char[],arg1:int,arg2:int) : void throws IOException\\l+ write(arg0:String,arg1:int,arg2:int) : void throws IOException\\l+ flush() : void throws IOException\\l+ close() : void throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Appendable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append(
				"label = \"{\\<\\<interface\\>\\>\\nAppendable|+ append(arg0:CharSequence) : Appendable throws IOException\\l+ append(arg0:CharSequence,arg1:int,arg2:int) : Appendable throws IOException\\l+ append(arg0:char) : Appendable throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("AutoCloseable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult
				.append("label = \"{\\<\\<interface\\>\\>\\nAutoCloseable|+ close() : void throws Exception\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Closeable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult
				.append("label = \"{\\<\\<interface\\>\\>\\nCloseable|+ close() : void throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Flushable [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult
				.append("label = \"{\\<\\<interface\\>\\>\\nFlushable|+ flush() : void throws IOException\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("Writer -> Closeable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("Writer -> Appendable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("Closeable -> AutoCloseable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("Writer -> Writer [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("Writer -> Flushable [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("Appendable -> Appendable [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("Writer -> Appendable [arrowhead=\"vee\",style=\"dashed\"]\n");
		expectedResult.append("OutputStreamWriter -> Writer [arrowhead=\"onormal\"]\n");
		expectedResult.append("}\n");
		assertEquals(expectedResult.toString(), v.toString());
	}
	
	@Test
	public void testMouseAdapter() throws Exception {
		String className = "java.awt.event.MouseAdapter";
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
		expectedResult.append("MouseListener [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\nMouseListener|+ mouseClicked(arg0:MouseEvent) : void\\l+ mousePressed(arg0:MouseEvent) : void\\l+ mouseReleased(arg0:MouseEvent) : void\\l+ mouseEntered(arg0:MouseEvent) : void\\l+ mouseExited(arg0:MouseEvent) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("MouseMotionListener [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\nMouseMotionListener|+ mouseDragged(arg0:MouseEvent) : void\\l+ mouseMoved(arg0:MouseEvent) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("MouseWheelListener [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\nMouseWheelListener|+ mouseWheelMoved(arg0:MouseWheelEvent) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("EventListener [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{\\<\\<interface\\>\\>\\nEventListener}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("MouseAdapter [\n");
		expectedResult.append("shape=\"record\",\n");
		expectedResult.append("label = \"{MouseAdapter|+ init() : void\\l+ mouseClicked(arg0:MouseEvent) : void\\l+ mousePressed(arg0:MouseEvent) : void\\l+ mouseReleased(arg0:MouseEvent) : void\\l+ mouseEntered(arg0:MouseEvent) : void\\l+ mouseExited(arg0:MouseEvent) : void\\l+ mouseWheelMoved(arg0:MouseWheelEvent) : void\\l+ mouseDragged(arg0:MouseEvent) : void\\l+ mouseMoved(arg0:MouseEvent) : void\\l}\"\n");
		expectedResult.append("];\n");
		expectedResult.append("MouseAdapter -> MouseWheelListener [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("MouseMotionListener -> EventListener [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("MouseAdapter -> MouseListener [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("MouseAdapter -> MouseMotionListener [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("MouseListener -> EventListener [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("MouseWheelListener -> EventListener [arrowhead=\"onormal\",style=\"dashed\"]\n");
		expectedResult.append("}\n");

		assertEquals(expectedResult.toString(), v.toString());
	}

}
