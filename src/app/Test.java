package app;



public class Test {

	public static void main(String[] args) throws Exception {
		Framework fw = new Framework();
		fw.loadConfig("input/input.txt");
		fw.Analyze();
	}
}
