package mySprites;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class CircleSprite extends AbstractSprite {

	public CircleSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		
		bound = new Rectangle2D.Double(x, y, width, height);
		Rectangle2D bounds = bound.getBounds2D();
		shape = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
	}

	@Override
	public void move(Dimension space) {
		Rectangle2D bounds = this.computeNewBoundsAfterMoving(space);
		bound = bounds;
		shape = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
	}
	
	
}
