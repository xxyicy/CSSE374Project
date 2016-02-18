package gui;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.sun.prism.Image;

import app.TMXXreader;
import dotExecutable.DotExecuter;

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
			g.drawString("Loading UML Diagram, please wait...", x + 200, y + 190);
			System.out.println("lalalal");
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							DotExecuter executer = new DotExecuter(reader.getDotPath(),
									reader.getOutputDir() + "/output.txt", reader.getOutputDir() + "/output.png");
							executer.execute();
							imageIcon = new ImageIcon(reader.getOutputDir() + "/output.png");
							imageIcon.getImage().flush();
							c.revalidate();
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
		retrieving = false;
	}

	@Override
	public int getIconWidth() {
		if (imageIcon != null) {
			return imageIcon.getIconWidth();
		} else {
			return 1800;
		}
	}

	@Override
	public int getIconHeight() {
		if (imageIcon != null) {
			return imageIcon.getIconHeight();
		} else {
			return 1200;
		}
	}

}
