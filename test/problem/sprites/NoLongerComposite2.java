package problem.sprites;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoLongerComposite2 extends AbstractSprite {
	List<ISprite> children;

	public NoLongerComposite2(double x, double y, double width, double height) {
		super(x, y, width, height);
		children = new ArrayList<ISprite>();
	}

	@Override
	public Iterator<ISprite> iterator() {
		return new CompositeSpriteIterator(children.iterator());
	}


	public void add() {
		children.add(new NoLongerComposite2(0,0,0,0));
	}

	
	@Override
	public void move(Dimension space) {
		for(ISprite s : children) {
			s.move(space);
		}
	}
}
