package CompositeExample;

public class Main1 {

	public static void main(String[] args) {
		Restaurant res = new Owner("Mr.Donald", 100000);
		Restaurant res2 = new Owner("Mr.Mike", 150000);
		Restaurant det = new Details("McDonalds", 5);
		det.add(res);
		Restaurant delivery = new Owner("Mr. Raj", 200000);
		Details dtg = new Details("delivery", 20);
		dtg.add(delivery);
		dtg.add(det);
		
	}
}