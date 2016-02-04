package problem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import edu.roshulman.csse374.editor.TextEditor;




public class TextEditorApp {
	public static void main(String[] args) throws Exception {
		SubstitutionCipher sub = new SubstitutionCipher();
		InputStream in = new FileInputStream("./input_output/in.txt");
		OutputStream out = new FileOutputStream("./input_output/out.txt");
		InputStream uIn  = new DecryptInput(in,sub);
		OutputStream uOut = new EncryptOutput(out, sub);
		
		TextEditor editor = new TextEditor(uIn, uOut);
		editor.execute();
	}	
}
