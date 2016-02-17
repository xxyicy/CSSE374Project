package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.sun.glass.events.KeyEvent;

public class ResultFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FIXED_WIDTH = 800;
	private static final int FIXED_HEIGHT = 600;
	private static final Dimension INITAL_SIZE = new Dimension(FIXED_WIDTH, FIXED_HEIGHT);

	public ResultFrame() {

		this.setTitle("Design Parser - Result");

		this.setPreferredSize(INITAL_SIZE);
		this.setResizable(false);

		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);

		// JMenuItem eMenuItem = new JMenuItem("Exit");
		JMenuItem hMenuItem = new JMenuItem("Help");
		JMenuItem aMenuItem = new JMenuItem("About");

		// file.add(eMenuItem);
		help.add(hMenuItem);
		help.add(aMenuItem);

		menubar.add(file);
		menubar.add(help);

		this.setJMenuBar(menubar);

		JPanel checkPanel = new JPanel();
		checkPanel.setPreferredSize(new Dimension(250, 1000));

		JPanel contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(1000, 1000));
		// JComponent component = new UMLImageComponeont();
		// component.add(new UMLImageProxy());
		// contentPanel.add(component);

		JScrollPane checkPane = new JScrollPane(checkPanel);
		checkPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.getContentPane().add(checkPane,BorderLayout.LINE_START);
		this.getContentPane().add(new JScrollPane(contentPanel), BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

}
