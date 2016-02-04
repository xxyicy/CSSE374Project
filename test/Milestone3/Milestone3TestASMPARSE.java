package Milestone3;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IMethod;
import app.Utility;
import asm.SequenceMethodVisitor;
import impl.Method;
import visitor.impl.SDEditOutputStream;

public class Milestone3TestASMPARSE {
	String[] args;
	String[] args1;
	private SDEditOutputStream v;

	@Before
	public void AppTTest() {
		args = new String[100];
		args1 = new String[100];
		Utility.APP_TYPE = Utility.APP_SD;
//		args[1] = "SD";
		args[0] = "app.App.main(String[] args)";
		args[1] = "4";
		args1[0] = "sample.TestClass2.testMethod1(AbstractComponent c)";
		args1[1] = "1";

	}

	@Test
	public void TestSD() throws IOException {
		String methodFQS = args[0];

		int depth = args.length == 2 ? Integer.valueOf(args[1]) : 5;

		String[] methodInfo = Utility.parseMethodSignature(methodFQS);
		String methodClassName = methodInfo[0];
		String methodName = methodInfo[1];
		List<String> params = new ArrayList<String>();
		for (int i = 2; i < methodInfo.length; i++) {
			params.add(methodInfo[i].split(" ")[0]);
		}
		IMethod startMethod = new Method(methodName, "", "", params, new ArrayList<String>(), methodClassName);

		List<String> classesRead = new ArrayList<String>();

		readClassAndMethods(startMethod, depth, classesRead);

		List<IMethod> ml = startMethod.getCalls();

		String className = "java/lang/String";
		String method = "equals";
		String param = "[java.lang.Object]";
		String m1 = "createUmlDiagram";
		String cn1 = "app/App";
		String p1 = "[java.lang.String]";

		for (int i = 0; i < ml.size(); i++) {
			IMethod m = ml.get(i);
			if (className.equals(m.getClassName()) && method.equals(m.getName()) && param.equals(m.getParamTypes())) {
				assertTrue(m.getClassName().equals("java/lang/String"));
				assertTrue(m.getName().equals("equals"));
				assertTrue(m.getParamTypes().equals("[java.lang.Object]"));

			}
			if (cn1.equals(m.getClassName()) && m1.equals(m.getName()) && p1.equals(m.getParamTypes())) {
				assertTrue(m.getClassName().equals("app/App"));
				assertTrue(m.getName().equals("createUmlDiagram"));
				assertTrue(m.getParamTypes().equals("[java.lang.String]"));

			}

		}

		// System.out.println(startMethod.getCalls().get(2).getName());

	}

	@Test
	public void Test2() throws IOException {
		String methodFQS = args1[0];

		int depth = args1.length == 2 ? Integer.valueOf(args[1]) : 5;

		String[] methodInfo = Utility.parseMethodSignature(methodFQS);
		String methodClassName = methodInfo[0];
		String methodName = methodInfo[1];
		List<String> params = new ArrayList<String>();
		for (int i = 2; i < methodInfo.length; i++) {
			params.add(methodInfo[i].split(" ")[0]);
		}
		IMethod startMethod = new Method(methodName, "", "", params, new ArrayList<String>(), methodClassName);

		List<String> classesRead = new ArrayList<String>();

		readClassAndMethods(startMethod, depth, classesRead);

		v = new SDEditOutputStream(new FileOutputStream("./output/output.txt"));
		v.write(startMethod);
		System.out.println(v.toString());
		System.out.println(startMethod.printCallChains(0));
		StringBuffer result = new StringBuffer();
		result.append("arg0:TestClass2\n");
		result.append("/arg1:ArrayList\n");
		result.append("/arg2:Rectangle\n");
		result.append("/arg3:WindowButton\n");
		result.append("\n");
		result.append("arg0:arg1.new\n");

		result.append("arg0:arg0.testMethod5():Button\n");
		result.append("arg0:arg2.new\n");
		result.append("arg0:arg3.new\n");

		assertEquals(result.toString(), v.toString());

	}

	public static void readClassAndMethods(IMethod current, int curDepth, List<String> classesRead) throws IOException {
		if (curDepth < 1) {
			return;
		}
		// add the class to read list

		ClassReader reader = new ClassReader(current.getClassName());
		ClassVisitor sequenceVisitor = new SequenceMethodVisitor(Opcodes.ASM5, current, current.getClassName());

		reader.accept(sequenceVisitor, ClassReader.EXPAND_FRAMES);

		// Recursive call to include all methods called within the range of
		// depth
		for (IMethod m : current.getCalls()) {
			readClassAndMethods(m, curDepth - 1, classesRead);
		}

	}

}
