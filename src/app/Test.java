package app;

import dotExecutable.DotExecuter;



public class Test {

	public static void main(String[] args) throws Exception {
//		Framework fw = new Framework();
//		fw.loadConfig("input/input.txt");
//		fw.Analyze();
		
		DotExecuter e = new DotExecuter("/usr/local/bin/dot","/Users/mot/Desktop/repositories/CSSE374Project/output/output.txt","/Users/mot/Desktop/repositories/CSSE374Project/output/output.png");
		e.execute();
	}
	
	
}
