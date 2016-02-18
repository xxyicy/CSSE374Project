package gui;


import java.awt.Component;
import java.awt.Graphics;


import javax.swing.Icon;
import javax.swing.ImageIcon;

import Framework.TMXXreader;
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
			g.drawString("Loading UML Diagram, please wait...", x + 300, y + 190);
			System.out.println("draw string");
			if (!retrieving) {
				retrieving = true;
				System.out.println("Creating thread");
				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							DotExecuter executer = new DotExecuter(reader.getDotPath(),
									reader.getOutputDir() + "/output.txt", reader.getOutputDir() + "/output.png");
							executer.execute();
							
							
							
							imageIcon = new ImageIcon(reader.getOutputDir() + "/output.png");
							// Image img = imageIcon.getImage();
							// Image newimg = img.getScaledInstance(800, 800,
							// java.awt.Image.SCALE_SMOOTH);
							// imageIcon = new ImageIcon(newimg);
							imageIcon.getImage().flush();
							c.repaint();
							c.revalidate();
							retrieving = false;
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
			return 0;
		}
	}

	@Override
	public int getIconHeight() {
		if (imageIcon != null) {
			return imageIcon.getIconHeight();
		} else {
			return 0;
		}
	}
	
	public boolean getRetrieving(){
		return retrieving;
	}

}
