package CompositeExample;

public interface Restaurant {

	public void add(Restaurant res);

	public void remove(Restaurant res);

	public Restaurant getRecommendation(int i);

	public String getRestaurant();

	public double getCost();

}
