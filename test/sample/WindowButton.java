package sample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class WindowButton extends Button {

	private static final int H_SPACE = 6;

	public WindowButton(String text, Rectangle bound) {
		super(text, bound);
	}

	@Override
	public void drawComponent(Graphics2D g){
		 Rectangle bound = this.getBounds();
			
		 // Draw the boarder after setting the thickness
		 g.setColor(new Color(48,180,29));
		 Rectangle2D border = new Rectangle2D.Float(bound.x, bound.y, bound.width,
		 bound.height);
		 g.setStroke(new BasicStroke(2));
		 g.draw(border);
		
		 // Draw the white fill
		 g.setColor(Color.WHITE);
		 Rectangle2D fill = new Rectangle2D.Float(bound.x+3, bound.y+3,
		 bound.width-3, bound.height-3);
		 g.fill(fill);;
		
		 // Draw the Fill
		 g.setFont(new Font("Verdana", Font.PLAIN, 17));
		 g.setColor(Color.blue);
		 g.drawString(this.text, bound.x + H_SPACE, bound.y + 16);

		 
	}

}
