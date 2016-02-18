package gui;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;

public class UMLImageComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Icon icon;

	public UMLImageComponent(Icon icon) {
		this.icon = icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int x = (1500 - w) / 2;
		int y = (1200 - h) / 2;
		icon.paintIcon(this, g, x, y);
	}

}
