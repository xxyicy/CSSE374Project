package mySprites;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public abstract class AbstractSprite implements ISprite  {
	protected double dx;
	protected double dy;
	protected ShapeComponent shape;
	protected Shape bound;
	

	// Subclasses need to chain this constructor
	public AbstractSprite(double x, double y, double width, double height) {
		this.dx = SpriteFactory.DX;
		this.dy = SpriteFactory.DY;
	}

	
	// Designed to be used by subclasses
	protected final Rectangle2D computeNewBoundsAfterMoving(Dimension space) {
		Rectangle2D bounds = bound.getBounds2D();
		
		if(bounds.getX() < 0 || bounds.getX() > space.getWidth())
			dx = -dx;

		if(bounds.getY() < 0 || bounds.getY() > space.getHeight())
			dy = -dy;
		
		Rectangle2D newBounds = new Rectangle2D.Double(bounds.getX() + dx,
														bounds.getY() + dy,
														bounds.getWidth(),
														bounds.getHeight());
		return newBounds;
	}
	
	
	@Override
	public final ShapeComponent getShape() {
		return this.shape;
	}
	
	@Override
	public abstract void move(Dimension space);
}
