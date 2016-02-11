package CompositeExample;

import java.util.ArrayList;
import java.util.List;

public class CompositeExample {
	private String name;
	private List<CompositeExample> location;

	public CompositeExample(String name) {
		this.name = name;
		location = new ArrayList<CompositeExample>();
	}

	public void add(CompositeExample e) {
		location.add(e);
	}

	public void remove(CompositeExample e) {
		location.remove(e);
	}

	public List<CompositeExample> getLocation() {
		return location;
	}

}

