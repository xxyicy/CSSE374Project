package app;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import api.IMethod;
import asm.SequenceMethodVisitor;
import impl.Method;
public class AppTest {
	String[] args ;
	
	@Before
	public void AppTTest(){
		args = new String[100];
//		args[1]="SD" ;
		args[0]="app.App.main(String[] args)";
		args[1] = "4";
				
	}
	@Test
	public void TestSD() throws IOException{
		String methodFQS = args[0];

		int depth = args.length == 2 ? Integer.valueOf(args[1]) : 5;
		
		String[] methodInfo = Utility.parseMethodSignature(methodFQS);
		String methodClassName = methodInfo[0];
		String methodName = methodInfo[1];
		List<String> params = new ArrayList<String>();
		for (int i = 2; i < methodInfo.length; i++) {
			params.add(methodInfo[i].split(" ")[0]);
		}
		IMethod startMethod = new Method(methodName, "", "", params,
				new ArrayList<String>(), methodClassName);
		//System.out.println(startMethod);
		//ArrayList<IMethod> st = new ArrayList<IMethod>();
		List<String> classesRead = new ArrayList<String>();
		
		Utility.readClassAndMethods(startMethod, depth, classesRead);
		List<IMethod> ml = startMethod.getCalls();
		
		String className = "java/lang/String";
		String method = "equals";
		String param = "[java.lang.Object]";
		String m1 ="createUmlDiagram";
		String cn1= "app/App";
		String p1 = "[java.lang.String]";
		
	//	String[]  
		for(int i=0;i<ml.size();i++){
			IMethod m = ml.get(i);
			if(className.equals(m.getClassName())&&method.equals(m.getName())&&param.equals(m.getParamTypes())){
				assertTrue(m.getClassName().equals("java/lang/String"));
				assertTrue(m.getName().equals("equals"));
				assertTrue(m.getParamTypes().equals("[java.lang.Object]"));
				
			}
			if(cn1.equals(m.getClassName())&&m1.equals(m.getName())&&p1.equals(m.getParamTypes())){
				assertTrue(m.getClassName().equals("app/App"));
				assertTrue(m.getName().equals("createUmlDiagram"));
				assertTrue(m.getParamTypes().equals("[java.lang.String]"));
				
			}
			
			
		}
		
		System.out.println(startMethod.getCalls().get(2).getName());

		
	}
	
}
