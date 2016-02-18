package forTest;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final int FIXED_WIDTH = 700;
	private static final int FIXED_HEIGHT = 300;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH, FIXED_HEIGHT);

	public AboutFrame() {
		this.setTitle("Help Framework");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);
		this.setLayout(new GridLayout(6, 1));
		JLabel message = new JLabel("About.....");
		JLabel line1 = new JLabel("Our project has 3 parts");
		JLabel line2 = new JLabel("1) Modified ASM part to read all classes and files");
		JLabel line3 = new JLabel("2) Internal Representation of classes and traversers of visitor pattern");
		JLabel line4 = new JLabel("3) Visitor of the visitor pattern which has string buffer to parse all class information ");
		

		this.add(message);
		this.add(line1);
		this.add(line2);
		this.add(line3);
		this.add(line4);
		
		this.pack();
		this.setVisible(true);

	}
}