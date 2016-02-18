package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


import javax.swing.JCheckBox;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import observer.api.Observer;

import com.sun.glass.events.KeyEvent;

import Framework.TMXXreader;
import Framework.Framework.DataBox;
import api.IClass;
import api.IModel;
import api.IPattern;
import modelAnalyzer.ModelVisitor;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.IOutputStream;

public class ResultFrame extends JFrame implements Observer {
	private IModel model;
	private TMXXreader reader;
	private IOutputStream outputStream;
	private HashMap<JCheckBox, ArrayList<JCheckBox>> patterns;
	private HashMap<JCheckBox, IPattern> classString;
	private ArrayList<IPattern> patternList;
	private ModelVisitor m;
	private UMLImageProxy proxy;
	private Thread imageThread;

	private class updatePatternList implements Runnable {
		@Override
		public void run() {
			try {
				
				m.setPatterns(patternList);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	private class ImageThread implements Runnable {
		private Object data;

		public ImageThread(Object data) {
			this.data = data;
		}

		@Override
		public void run() {
			try {
				ResultFrame.this.updateImage(data);
			} catch (Exception ignore) {
				
			}

		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FIXED_WIDTH = 1400;
	private static final int FIXED_HEIGHT = 850;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH,
			FIXED_HEIGHT);

	public ResultFrame(DataBox box) {
		this.model = box.getModel();
		this.reader = box.getReader();
		this.patterns = new HashMap<JCheckBox, ArrayList<JCheckBox>>();
		this.classString = new HashMap<JCheckBox, IPattern>();
		this.patternList = new ArrayList<IPattern>();

		m = new ModelVisitor(model);
		m.registerObserver(this);

		this.setTitle("Design Parser - Result");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);

		this.addMenuBar();

		JPanel checkPanel = new JPanel();
		checkPanel.setBackground(Color.white);
		checkPanel.setPreferredSize(new Dimension(400, 1500));
		addPattern(checkPanel);

		proxy = new UMLImageProxy(reader);
		UMLImageComponent component = new UMLImageComponent(proxy);
		component.setPreferredSize(new Dimension(10000, 10000));
		JScrollPane scrollPane = new JScrollPane(component);

		JScrollPane checkPane = new JScrollPane(checkPanel);
		checkPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.getContentPane().add(checkPane, BorderLayout.LINE_START);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
		
		
		
	}

	private void addMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);

		JMenuItem rMenuItem = new JMenuItem("Restart");
		rMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LandingPage frame = new LandingPage();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setVisible(false);
				dispose();
			}

		});
		JMenuItem eMenuItem = new JMenuItem("Export");
		eMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// HelpFrame frame = new HelpFrame();
			}

		});
		JMenuItem hMenuItem = new JMenuItem("Help");
		hMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HelpFrame frame = new HelpFrame();
			}

		});
		JMenuItem aMenuItem = new JMenuItem("About");
		aMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutFrame frame = new AboutFrame();
			}

		});

		file.add(rMenuItem);
		file.add(eMenuItem);
		help.add(hMenuItem);
		help.add(aMenuItem);

		menubar.add(file);
		menubar.add(help);

		this.setJMenuBar(menubar);
	}

	private void addPattern(JPanel checkPanel) {
		checkPanel.setLayout(null);
		Insets insets = checkPanel.getInsets();
		for (IPattern p : model.getPatterns()) {
			String name = p.getName();
			String className = "";
			Color color = Color.black;
			for (IClass c : p.getClasses()) {
				switch (name) {
				case "Singleton":
					if (c.getTags().contains("Singleton")) {
						className = c.getName();
					}
					color = Color.blue;
					break;
				case "Adapter":
					if (c.getTags().contains("adapter")) {
						className = c.getName();
					}
					color = Color.red;
					break;
				case "Composite":
					if (c.getTags().contains("Composite")) {
						className = c.getName();
					}
					color = Color.yellow;
					break;
				case "Decorator":
					if (c.getTags().contains("decorator")) {
						className = c.getName();
					}
					color = Color.GREEN;
					break;
				}
			}

			boolean exist = false;
			JCheckBox cb = new JCheckBox(name, true);
			ArrayList<JCheckBox> value = new ArrayList<JCheckBox>();
			for (JCheckBox c : patterns.keySet()) {
				if (c.getText().equals(name)) {
					exist = true;
					cb = c;
					value = patterns.get(c);
				}
			}

			if (!exist) {
				cb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						patternCheckBoxAction(e);
					}
				});
				cb.setForeground(color);
			}
			JCheckBox cb1;
			cb1 = new JCheckBox(className, true);

			cb1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					classCheckBoxAction(e);
				}

			});
			cb1.setForeground(color);
			value.add(cb1);
			patterns.put(cb, value);
			classString.put(cb1,p);
		}
		int height = 5;
		for (JCheckBox cb : patterns.keySet()) {
			checkPanel.add(cb);
			cb.setBounds(5 + insets.left, height + insets.top,
					cb.getPreferredSize().width, cb.getPreferredSize().height);
			height += 25;
			for (JCheckBox cb1 : patterns.get(cb)) {
				checkPanel.add(cb1);
				cb1.setBounds(25 + insets.left, height + insets.top,
						cb1.getPreferredSize().width,
						cb1.getPreferredSize().height);
				height += 25;
			}
		}
		
		
		
		this.firePatternThread();
		
	}
	
	private void firePatternThread(){
		patternList = new ArrayList<IPattern>();
		for (JCheckBox b : patterns.keySet()) {
			for (JCheckBox b1 : patterns.get(b)) {
				if (b1.isSelected()) {
					patternList.add(classString.get(b1));
				}
			}
		}
		Thread t = new Thread(new updatePatternList());
		t.start();
	}
	

	private void patternCheckBoxAction(ActionEvent e) {
		// if (proxy.getRetrieving()){
		// return;
		// }

		JCheckBox checkBox = (JCheckBox) e.getSource();
		boolean selected = checkBox.getModel().isSelected();
		
		if (selected) {
			ArrayList<JCheckBox> values = patterns.get(checkBox);
			for (JCheckBox b : values) {
				b.setSelected(true);

			}
		}
		
		this.firePatternThread();

	}

	private void classCheckBoxAction(ActionEvent e) {
		// if (proxy.getRetrieving()){
		// return;
		// }

		JCheckBox checkBox = (JCheckBox) e.getSource();
		boolean selected = checkBox.getModel().isSelected();
		
		
		this.firePatternThread();

	}

	private void updateImage(Object data) {
		if (data instanceof IModel) {
			IModel m = (IModel) data;
			try {
				this.outputStream = new GraphVizOutputStream(
						new FileOutputStream(reader.getOutputDir()
								+ "/output.txt"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			outputStream.start();
			outputStream.write(m);
			outputStream.end();
			proxy.clearImageIcon();

			revalidate();
			repaint();

			
		}
	}

	@Override
	public void update(Object data) {
		if (this.imageThread != null) {
			this.imageThread.interrupt();
		}

		this.imageThread = new Thread(new ImageThread(data));
		this.imageThread.start();

	}

}
