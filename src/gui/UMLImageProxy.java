package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import app.TMXXreader;

public class UMLImageProxy implements Icon {
	ImageIcon imageIcon = null;
	String outputPath;
	TMXXreader reader;
	Thread retrievalThread;
	boolean retrieving = false;

	public UMLImageProxy(TMXXreader reader) {
		this.reader = reader;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (imageIcon != null) {
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading UML Diagram, please wait...", x + 300, y + 190);
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							imageIcon = new ImageIcon(reader.getOutputDir()+"output.png");
							// imageIcon = new ImageIcon(imageURL, "CD Cover");
							c.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
		}
	}

	public void clearImageIcon() {
		imageIcon = null;
	}

	@Override
	public int getIconWidth() {
		if (imageIcon != null) {
			return imageIcon.getIconWidth();
		} else {
			return 400;
		}
	}

	@Override
	public int getIconHeight() {
		if (imageIcon != null) {
			return imageIcon.getIconHeight();
		} else {
			return 300;
		}
	}

}
