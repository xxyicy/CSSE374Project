package forTest;

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
		int x = 10;
		int y = 10;
		icon.paintIcon(this, g, x, y);
	}

}
