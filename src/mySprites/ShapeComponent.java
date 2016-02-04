package mySprites;

import java.awt.Shape;
import java.util.*;

public abstract class ShapeComponent  {
   
	public void add(ShapeComponent shapeComponent) {
		throw new UnsupportedOperationException();
	}
	public void remove(ShapeComponent shapeComponent) {
		throw new UnsupportedOperationException();
	}
	public ShapeComponent getChild(int i) {
		throw new UnsupportedOperationException();
	}
  
	public Shape getShape(){
		throw new UnsupportedOperationException();
	}

	public abstract Iterator createIterator();
 
	public void print() {
		throw new UnsupportedOperationException();
	}
}
