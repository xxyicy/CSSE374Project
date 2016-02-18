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
							Thread.sleep(3000);
							
							imageIcon = new ImageIcon(reader.getOutputDir() + "/streamWriter.png");
							System.out.println(imageIcon);
							// imageIcon = new ImageIcon(imageURL, "CD Cover");
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
			return 1500;
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

//	private BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight,
//			boolean preserveAlpha) {
//		System.out.println("resizing...");
//		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
//		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
//		Graphics2D g = scaledBI.createGraphics();
//		if (preserveAlpha) {
//			g.setComposite(AlphaComposite.Src);
//		}
//		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
//		g.dispose();
//		return scaledBI;
//	}

}
