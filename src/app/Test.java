package app;



public class Test {

	public static void main(String[] args) throws Exception {
		TMXXreader reader= new TMXXreader("input/input.txt");
		reader.readFile();
		
		Framework fw = reader.constructFramework();
		System.out.println(fw);
	}
}
