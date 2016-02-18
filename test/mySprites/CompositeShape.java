package mySprites;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;

import mySprites.CompositeIterator;

public class CompositeShape extends ShapeComponent {
	ArrayList<ShapeComponent> shapeComponents = new ArrayList<ShapeComponent>();
	
	
	public void add(ShapeComponent shapeComponent) {
		this.shapeComponents.add(shapeComponent);
	}
	public void remove(ShapeComponent shapeComponent) {
		this.shapeComponents.remove(shapeComponent);
	}
	public ShapeComponent getChild(int i) {
		return this.shapeComponents.get(i);
	}
	
	@Override
	public Shape getShape(){
		return null;
	}
	
	@Override
	public Iterator createIterator() {
		return new CompositeIterator(shapeComponents.iterator());
	}

}
