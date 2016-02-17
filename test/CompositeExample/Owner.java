package CompositeExample;

public class Owner implements Restaurant {

	private String name;
	private double cost;

	public Owner(String name, double cost) {
		this.name = name;
		this.cost = cost;
	}

	public void add(Restaurant res) {
		
	}

	public Restaurant getRecommendation(int i) {
		return null;
	}

	public String getRestaurant() {
		return name;
	}

	public double getCost() {
		return cost;
	}

	public void remove(Restaurant res) {
		
	}

}