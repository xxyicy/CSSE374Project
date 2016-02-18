package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import observer.api.Observer;

import com.sun.glass.events.KeyEvent;

import api.IClass;
import api.IModel;
import api.IPattern;
import app.Framework.DataBox;
import app.TMXXreader;
import modelAnalyzer.ModelVisitor;
import visitor.impl.GraphVizOutputStream;
import visitor.impl.IOutputStream;

public class ResultFrame extends JFrame implements Observer {
	private IModel model;
	private TMXXreader reader;
	private IOutputStream outputStream;
	private HashMap<JCheckBox, ArrayList<JCheckBox>> patterns;
	private HashMap<String, IPattern> classString;
	private ArrayList<IPattern> patternList;
	private ModelVisitor m;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FIXED_WIDTH = 800;
	private static final int FIXED_HEIGHT = 600;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH, FIXED_HEIGHT);

	public ResultFrame(DataBox box) {
		this.model = box.getModel();
		this.reader = box.getReader();
		this.patterns = new HashMap<JCheckBox, ArrayList<JCheckBox>>();
		this.classString = new HashMap<String, IPattern>();
		this.patternList = new ArrayList<IPattern>();
		
		m = new ModelVisitor(model);
		m.registerObserver(this);
		try {
			this.outputStream = new GraphVizOutputStream(new FileOutputStream(reader.getOutputDir() + "/output.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		this.setTitle("Design Parser - Result");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);

		this.addMenuBar();

		JPanel checkPanel = new JPanel();
		checkPanel.setBackground(Color.white);
		checkPanel.setPreferredSize(new Dimension(250, 1000));
		addPattern(checkPanel);

		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.white);
		contentPanel.setPreferredSize(new Dimension(1000, 1000));
		JComponent component = new UMLImageComponeont(new UMLImageProxy());
		contentPanel.add(component);

		JScrollPane checkPane = new JScrollPane(checkPanel);
		checkPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.getContentPane().add(checkPane, BorderLayout.LINE_START);
		this.getContentPane().add(new JScrollPane(contentPanel), BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

		run();
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
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
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
		HashMap<String, List> map = new HashMap<String, List>();
		HashMap<String, Color> colors = new HashMap<String, Color>();
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
					if (c.getTags().contains("dapter")) {
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
			if (map.containsKey(name)) {
				List<String> value = map.get(name);
				value.add(className);
				map.put(name, value);
			} else {
				List<String> value = new ArrayList<String>();
				value.add(className);
				map.put(name, value);
				colors.put(name, color);
			}
			classString.put(className, p);
			patternList.add(p);
		}
		int height = 5;
		for (String pattern : map.keySet()) {
			JCheckBox cb = new JCheckBox(pattern, true);
			cb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					patternCheckBoxAction(e);
				}
			});
			cb.setForeground(colors.get(pattern));
			checkPanel.add(cb);
			cb.setBounds(5 + insets.left, height + insets.top, cb.getPreferredSize().width,
					cb.getPreferredSize().height);
			height += 25;
			List<String> value = map.get(pattern);
			ArrayList<JCheckBox> values = new ArrayList<JCheckBox>();
			patterns.put(cb, values);
			for (String className : value) {
				JCheckBox cb1 = new JCheckBox(className, true);
				values = patterns.get(cb);
				values.add(cb1);
				patterns.put(cb, values);
				cb1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						classCheckBoxAction(e);
					}

				});
				cb1.setForeground(colors.get(pattern));
				checkPanel.add(cb1);
				cb1.setBounds(25 + insets.left, height + insets.top, cb1.getPreferredSize().width,
						cb1.getPreferredSize().height);
				height += 25;
			}

		}
	}

	private void patternCheckBoxAction(ActionEvent e) {
		JCheckBox checkBox = (JCheckBox) e.getSource();
		boolean selected = checkBox.getModel().isSelected();
		if (selected) {
			ArrayList<JCheckBox> values = patterns.get(checkBox);
			for (JCheckBox b : values) {
				b.setSelected(true);
				if (!patternList.contains(classString.get(b.getText()))) {
					patternList.add(classString.get(b.getText()));
				}
			}
		}
		run();
		System.out.println(patternList.size());

	}

	private void classCheckBoxAction(ActionEvent e) {
		JCheckBox checkBox = (JCheckBox) e.getSource();
		boolean selected = checkBox.getModel().isSelected();
		if (selected) {
			if (!patternList.contains(classString.get(checkBox.getText()))) {
				patternList.add(classString.get(checkBox.getText()));
			}
		} else {
			if (patternList.contains(classString.get(checkBox.getText()))) {
				patternList.remove(classString.get(checkBox.getText()));
			}
		}
		run();
	
	}

	private void run() {
		m.setPatterns(patternList);
		

	
	}

	@Override
	public void update(Object data) {
		if(data instanceof IModel){
			IModel m = (IModel) data;
			outputStream.start();
			outputStream.write(m);
			outputStream.end();
			System.out.println("write to file");
		}
	}

}
