package app;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelpFramework extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final int FIXED_WIDTH = 350;
	private static final int FIXED_HEIGHT = 300;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH, FIXED_HEIGHT);
	
	public HelpFramework(){
		this.setTitle("Help Framework");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);
		this.setLayout(new GridLayout(6, 1));
		JLabel message = new JLabel("How to go about the app..........");
		
		this.add(message);
		this.pack();
		this.setVisible(true);
		
		

	}
}
