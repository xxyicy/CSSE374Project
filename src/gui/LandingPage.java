package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;


import Framework.Framework;
import Framework.Framework.DataBox;
import Framework.Framework.ProgressBox;

import observer.api.Observer;

public class LandingPage extends JFrame implements Observer {

	private class updateRunnable implements Runnable {
		@Override
		public void run() {
			try {
				LandingPage.this.fw.Analyze();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	private Framework fw;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int FIXED_WIDTH = 350;
	private static final int FIXED_HEIGHT = 300;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH,
			FIXED_HEIGHT);
	private JLabel message;
	private JProgressBar progressBar;

	public LandingPage() throws FileNotFoundException, IOException {
		this.fw = new Framework();
		fw.registerObserver(this);
		this.setTitle("Design Parser");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);
		this.setLayout(new GridLayout(5, 1));
		JTextField pathText = new JTextField(20);
		JPanel btnPanel = new JPanel();

		JButton load = new JButton();
		load.setText("Load Config");
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LandingPage.this.loadConfig();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});

		JButton analy = new JButton();
		analy.setText("Analyze");

		analy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LandingPage.this.Analyze();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(load);
		btnPanel.add(analy);

		message = new JLabel("Analyzing");
		message.setHorizontalAlignment(JLabel.CENTER);

		JPanel progressPanel = new JPanel();

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);

		progressPanel.add(progressBar);

		this.getContentPane().add(new JPanel());
		this.getContentPane().add(btnPanel);
		this.getContentPane().add(message);
		this.getContentPane().add(progressPanel);
		this.pack();
		this.setVisible(true);
	}

	public void loadConfig() throws IOException {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(LandingPage.this.getContentPane());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filePath = fc.getSelectedFile().getAbsolutePath();
			this.fw.loadConfig(filePath);
		} else {

		}
		
	}

	public void Analyze() throws Exception {
		Thread t = new Thread(new updateRunnable());
		t.start();
	}

	@Override
	public void update(Object data) {
		if (data instanceof DataBox) {
			
			DataBox m = (DataBox) data;
			ResultFrame frame = new ResultFrame(m);
			this.setVisible(false);
			this.dispose();
			return;
		}
		ProgressBox box = (ProgressBox) data;
		this.message.setText(box.getCurrentTask());
		this.progressBar.setValue((int) box.getProgress());
	}

}
