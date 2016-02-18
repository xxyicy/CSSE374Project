package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelpFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final int FIXED_WIDTH = 500;
	private static final int FIXED_HEIGHT = 300;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH, FIXED_HEIGHT);
	
	public HelpFrame(){
		this.setTitle("Help Framework");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);
		this.setLayout(new GridLayout(6, 1));
		JLabel message = new JLabel("How to go about the app...");
		JLabel line1  = new JLabel("i) Open your Terminal window");
		JLabel line2  = new JLabel("ii) Go to your directory");
		JLabel line3  = new JLabel("iii) Parse the given directory to print the texture representation");
		JLabel line4 = new JLabel("iv)To get UML:  dot -Tpng output/output.txt > <output-file-name>");
		
		this.add(message);
		this. add(line1);
		this. add(line2);
		this. add(line3);
		this. add(line4);
		
		this.pack();
		this.setVisible(true);
		
		

	}
}
