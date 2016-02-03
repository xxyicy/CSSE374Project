package sample;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class TestClass2 extends TestClass1{
	private TestClass1 testField;
	public static int count;
	private List<Button> buttons;
	
	public void testMethod1(AbstractComponent c){
		// just for test;
		buttons = new ArrayList<Button>();
		testMethod5();
	}
	
	public String testMethod3(){
		return "";
	}
	
	public Button testMethod4(){
		Button b = new LinuxButton("a", new Rectangle(1,1,1,1));
		return b;
	}
	
	
	public Button testMethod5(){
		Button b  = new WindowButton("a",new Rectangle(1,1,1,1));
		return b;
	}
}
