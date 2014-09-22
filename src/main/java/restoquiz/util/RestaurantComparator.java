package restoquiz.util;

import java.util.Comparator;

import restoquiz.model.Restaurant;

public class RestaurantComparator implements Comparator<Restaurant> {

	@Override
	public int compare(Restaurant arg0, Restaurant arg1) {
		Integer votosArg0 = new Integer(arg0.getVotos());
		Integer votosArg1 = new Integer(arg1.getVotos());
		
		return votosArg0.compareTo(votosArg1);
	}

}
