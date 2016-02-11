package CompositeExample;

import java.util.ArrayList;
import java.util.List;

public class Details implements Restaurant {

	private String name;
	private double cost;

	public Details(String name, double cost) {
		this.name = name;
		this.cost = cost;
	}

	List<Restaurant> restaurant = new ArrayList<Restaurant>();

	public void add(Restaurant res) {
		restaurant.add(res);
	}

	public String getRestaurant() {
		return name;
	}

	public double getCost() {
		return cost;
	}

	public void remove(Restaurant res) {
		restaurant.remove(res);
	}

	@Override
	public Restaurant getRecommendation(int i) {
		// TODO Auto-generated method stub
		return restaurant.get(i);

	}

}