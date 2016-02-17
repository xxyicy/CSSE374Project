package app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class LandingPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int FIXED_WIDTH = 350;
	private static final int FIXED_HEIGHT = 300;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH, FIXED_HEIGHT);

	public LandingPage() {

		this.setTitle("Design Parser");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);
		this.setLayout(new GridLayout(6, 1));

		this.add(new JPanel());
		this.addButton();

		JPanel pathPanel = new JPanel();
		pathPanel.setLayout(new FlowLayout());

		JLabel pathLabel = new JLabel("Config File Path: ");
		pathLabel.setHorizontalAlignment(JLabel.LEFT);

		JTextField pathText = new JTextField(20);

		pathPanel.add(pathLabel);
		pathPanel.add(pathText);

		JLabel message = new JLabel("Analyzing");
		message.setHorizontalAlignment(JLabel.CENTER);

		JPanel progressPanel = new JPanel();

		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);

		progressPanel.add(progressBar);

		this.add(new JPanel());

		this.add(pathPanel);
		this.add(new JPanel());
		this.add(message);
		this.add(progressPanel);
		this.pack();
		this.setVisible(true);
	}

	private void addButton() {
		JPanel btnPanel = new JPanel();

		JButton load = new JButton();
		load.setText("Load Config");
//		load.addActionListener(new ActionListener() {
//
//		});

		JButton analy = new JButton();
		analy.setText("Analyze");

		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(load);
		btnPanel.add(analy);

		this.add(btnPanel);
	}

}
