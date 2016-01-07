package sample;

import java.awt.Rectangle;
import java.util.ArrayList;


public class TestClass2 {
	private TestClass1 testField;
	
	public void testMethod1(Button b, LinuxButton c){
		// just for test;
	}
	
	public void testMethod2(ArrayList<LinuxButton> arr){
		// just for test
	}
	
	public TestClass1 testMethod3(){
		return testField;
	}
	
	public Button testMethod4(){
		Button b = new LinuxButton("a", new Rectangle(1,1,1,1));
		return b;
	}
}
