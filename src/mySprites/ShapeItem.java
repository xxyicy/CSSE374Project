package mySprites;

import java.awt.Shape;
import java.util.Iterator;

public class ShapeItem extends ShapeComponent {
	private Shape shape;
	
	public ShapeItem(Shape shape){
		this.shape = shape;
	}
	
	@Override
	public Iterator createIterator() {
		return new NullIterator();
	}
	
	@Override
	public Shape getShape(){
		return this.shape;
	}
	
	
}
