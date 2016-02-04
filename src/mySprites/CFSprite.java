package mySprites;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class CFSprite extends AbstractSprite {

	public CFSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		bound = new Rectangle2D.Double(x, y, width, height);
		Rectangle2D bounds = bound.getBounds2D();
		ShapeItem circle = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()/2));
		ShapeItem rec = new ShapeItem(new Rectangle2D.Double(bounds.getX(), bounds.getY()+bounds.getHeight()/2, bounds.getWidth(), bounds.getHeight()/2));
		
		
		ShapeItem cir1 = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		ShapeItem rec1 = new ShapeItem(new Rectangle2D.Double(bounds.getX()+bounds.getWidth()/2, bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		CompositeShape s1 = new CompositeShape();
		s1.add(cir1);
		s1.add(rec1);
		
		CompositeShape shapes = new CompositeShape();
		shapes.add(s1);
		shapes.add(circle);
		shapes.add(rec);
		shape = shapes;
	}

	@Override
	public void move(Dimension space) {
		Rectangle2D bounds = this.computeNewBoundsAfterMoving(space);
		bound = bounds;
		
		
		ShapeItem cir1 = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		ShapeItem rec1 = new ShapeItem(new Rectangle2D.Double(bounds.getX()+bounds.getWidth()/2, bounds.getY(), bounds.getWidth()/2, bounds.getHeight()));
		CompositeShape s1 = new CompositeShape();
		s1.add(cir1);
		s1.add(rec1);
		
		ShapeItem circle = new ShapeItem(new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()/2));
		ShapeItem rec = new ShapeItem(new Rectangle2D.Double(bounds.getX(), bounds.getY()+bounds.getHeight()/2, bounds.getWidth(), bounds.getHeight()/2));
		CompositeShape shapes = new CompositeShape();
		shapes.add(circle);
		shapes.add(rec);
		shapes.add(s1);
		shape = shapes;
	}
	
	
}
