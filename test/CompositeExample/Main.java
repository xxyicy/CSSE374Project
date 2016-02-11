package CompositeExample;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CompositeExample TerreHaute = new CompositeExample("Chavas");
		CompositeExample Theme = new CompositeExample("Restaurant");
		CompositeExample Street = new CompositeExample("Wabash");
		CompositeExample Menu = new CompositeExample("Burrito");
		CompositeExample shopNo = new CompositeExample("2nd shop");
		CompositeExample shopMade = new CompositeExample("OldLook");
		TerreHaute.add(Theme);
		TerreHaute.add(Street);
		Theme.add(Menu);
		Street.add(shopNo);
		Street.add(shopMade);

	}

}
