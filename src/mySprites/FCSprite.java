package mySprites;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class FCSprite extends AbstractSprite {

	public FCSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		bound = new Rectangle2D.Double(x, y, width, height);
		Rectangle2D bounds = bound.getBounds2D();
		
		ShapeItem circle = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		ShapeItem rec = new ShapeItem(new Rectangle2D.Double(bounds.getX()+bounds.getWidth()/2, bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		CompositeShape shapes = new CompositeShape();
		shapes.add(circle);
		shapes.add(rec);
		shape = shapes;
	}

	@Override
	public void move(Dimension space) {
		Rectangle2D bounds = this.computeNewBoundsAfterMoving(space);
		bound = bounds;
		
		ShapeItem circle = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		ShapeItem rec = new ShapeItem(new Rectangle2D.Double(bounds.getX()+bounds.getWidth()/2, bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		CompositeShape shapes = new CompositeShape();
		shapes.add(circle);
		shapes.add(rec);
		shape = shapes;
	}
	
	
}
