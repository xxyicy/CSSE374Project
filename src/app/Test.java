package app;



public class Test {

	public static void main(String[] args) throws Exception {
		TMXXreader reader= new TMXXreader("input/input.txt");
		reader.readFile();
		
		System.out.println(reader);
	}
}
