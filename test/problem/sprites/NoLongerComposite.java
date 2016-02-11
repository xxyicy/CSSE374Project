package problem.sprites;

import java.awt.Dimension;

import java.util.Iterator;


public class NoLongerComposite extends AbstractSprite {
	ISprite children;

	public NoLongerComposite(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public Iterator<ISprite> iterator() {
		return new CompositeSpriteIterator(children.iterator());
	}

	@Override
	public void add(ISprite s) {
		children = s;
	}


	

	@Override
	public void move(Dimension space) {
		for(ISprite s : children) {
			s.move(space);
		}
	}
}
