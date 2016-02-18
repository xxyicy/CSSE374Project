package app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import modelAnalyzer.ModelVisitor;
import api.IModel;
import app.Framework.DataBox;
import app.Framework.ProgressBox;
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
		this.setLayout(new GridLayout(6, 1));
		JTextField pathText = new JTextField(20);
		JPanel btnPanel = new JPanel();

		JButton load = new JButton();
		load.setText("Load Config");
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = pathText.getText();
				try {
					LandingPage.this.loadConfig(text);
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

		JPanel pathPanel = new JPanel();

		JLabel pathLabel = new JLabel("Config File Path: ");

		pathPanel.add(pathLabel);
		pathPanel.add(pathText);

		message = new JLabel("Analyzing");
		message.setHorizontalAlignment(JLabel.CENTER);

		JPanel progressPanel = new JPanel();

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);

		progressPanel.add(progressBar);

		this.getContentPane().add(new JPanel());
		this.getContentPane().add(btnPanel);
		this.getContentPane().add(pathPanel);
		this.getContentPane().add(new JPanel());
		this.getContentPane().add(message);
		this.getContentPane().add(progressPanel);
		this.pack();
		this.setVisible(true);
	}

	public void loadConfig(String path) throws IOException {
		this.fw.loadConfig(path);
	}

	public void Analyze() throws Exception {
		Thread t = new Thread(new updateRunnable());
		t.start();
	}

	@Override
	public void update(Object data) {
		if (data instanceof DataBox) {
			
			DataBox m = (DataBox) data;
			
			
			ModelVisitor v = new ModelVisitor(m.getModel());
			v.visitModel();
			System.out.println(m.getModel());
			
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
