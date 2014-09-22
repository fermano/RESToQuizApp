package restoquiz.fakedatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import restoquiz.fakedatabase.jsonparser.RESToQuizJSONParser;
import restoquiz.model.Professional;
import restoquiz.model.Restaurant;
import restoquiz.util.RestaurantComparator;


public class FakeDatabase {
	private RESToQuizJSONParser parser;
	
	public FakeDatabase() {
		parser = new RESToQuizJSONParser();
	}
	
	public String vote(String restaurantId, String professionalId) {
		String result = "";
		List<Restaurant> restaurants = this.getRestaurants();
		List<Professional> professionals = this.getProfessionals();
		boolean voteChanged = false;
		
		for(Restaurant restaurant :  restaurants) {
			if (restaurantId.equalsIgnoreCase(restaurant.getId()) && !restaurant.isEleitoNaSemana()) {
				restaurant.setVotos(restaurant.getVotos()+1);
				voteChanged = true;
				break;
			}
		}
		
		if (!voteChanged) {
			result += "Restaurante bom da vontade de repetir mesmo, mas nao pode repetir na mesma semana.";
		}
		
		for(Professional professional : professionals) {
			if (professionalId.equalsIgnoreCase(professional.getId()) && !professional.isVotouHoje()) {
				professional.setVotouHoje(true);
				voteChanged = true;
				break;
			}
		}
		
		if (voteChanged) {
			result += saveRestaurants(restaurants);
			result += saveProfessionals(professionals);
		} else {
			result += "Voce ja votou hoje. Volte amanha!\n";
		}
		
		return result;
		
	}
	
	public String saveRestaurants(List<Restaurant> restaurants) {
		String result = "";
		try {
			parser.setRestaurants(restaurants);
		} catch (IOException e) {
			result = e.getMessage();
		}
		return result;
	}
	
	public String saveProfessionals(List<Professional> professionals) {
		String result = "";
		try {
			parser.setProfessionals(professionals);
		} catch (IOException e) {
			result = e.getMessage();
		}
		return result;
	}
	
	public List<Restaurant> getRestaurants() {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		JSONArray jsonRestaurants = parser.getRestaurants();
		
		for(Object o : jsonRestaurants) {
			JSONObject jsonRestaurant = (JSONObject) o;
			Restaurant restaurant = new Restaurant();
			restaurant.setName((String) jsonRestaurant.get("name"));
			restaurant.setId((String) jsonRestaurant.get("id"));
			Long votos = (Long) jsonRestaurant.get("votos");
			if(votos != null) {
				restaurant.setVotos(votos.intValue());	
			} else {
				restaurant.setVotos(0);
			}
			if (jsonRestaurant.get("eleitoNaSemana") == null || "false".equalsIgnoreCase(jsonRestaurant.get("eleitoNaSemana").toString())) {
				restaurant.setEleitoNaSemana(false);
			} else {
				restaurant.setEleitoNaSemana(true);
			}
		    restaurants.add(restaurant);
		}
		
		return restaurants;
	}
	
	public List<Restaurant> getAvailableRestaurants() {
		List<Restaurant> restaurants = this.getRestaurants();
		List<Restaurant> availRestaurants = new ArrayList<Restaurant>();
		
		for(Restaurant restaurant : restaurants) {
			if (!restaurant.isEleitoNaSemana()) {
				availRestaurants.add(restaurant);
			}
		}
		return availRestaurants;
	}
	
	public Restaurant getRestaurant(String id) {
		
		for (Restaurant restaurant : this.getRestaurants()) {
			if(id.equalsIgnoreCase(restaurant.getId())) {
				return restaurant;
			}
		}
		
		return null;
	}
	
	public List<Professional> getProfessionals() {
		List<Professional> professionals = new ArrayList<Professional>();
		
		JSONArray jsonProfessionals = parser.getProfessionals();
		
		for(Object o : jsonProfessionals) {
			JSONObject jsonProfessional = (JSONObject) o;
			Professional professional = new Professional();
			professional.setName((String) jsonProfessional.get("name"));
			professional.setId((String) jsonProfessional.get("id"));
			if (jsonProfessional.get("votouHoje") == null || "false".equalsIgnoreCase(jsonProfessional.get("votouHoje").toString())) {
				professional.setVotouHoje(false);
			} else {
				professional.setVotouHoje(true);
			}
			professionals.add(professional);
		}
		
		return professionals;
	}
	
	public Professional getProfessional(String id) {
		
		for (Professional professional : this.getProfessionals()) {
			if(id.equalsIgnoreCase(professional.getId())) {
				return professional;
			}
		}
		
		return null;
	}
	
	public List<Restaurant> getResults() {
		List<Restaurant> restaurants = this.getRestaurants();
		Collections.sort(restaurants, new RestaurantComparator());
		Collections.reverse(restaurants);
		
		return restaurants;
	}
	
	public void finishDay() {
		//Restaurants: get restaurants in VOTING order (top to bottom), sets the first one as elected and finally assign 0 to all votes
		List<Restaurant> restaurants = this.getRestaurants();
		Collections.sort(restaurants, new RestaurantComparator());
		Collections.reverse(restaurants);
		Restaurant restaurant = restaurants.get(0);
		restaurant.setEleitoNaSemana(true);
		restaurant.setVotos(0);
		
		for (int i = 1; i < restaurants.size(); i++) {
			restaurant = restaurants.get(i);
			restaurant.setVotos(0);
		}
		//Professionals: assign false to all votouHoje
		List<Professional> professionals = this.getProfessionals();
		
		for(Professional professional : professionals) {
			professional.setVotouHoje(false);
		}
		
		saveRestaurants(restaurants);
		saveProfessionals(professionals);
	}
	
	public void finishWeek() {
		List<Restaurant> restaurants = this.getRestaurants();
		
		for (Restaurant restaurant : restaurants) {
			restaurant.setVotos(0);
			restaurant.setEleitoNaSemana(false);
		}
		//Professionals: assign false to all votouHoje
		List<Professional> professionals = this.getProfessionals();
		
		for(Professional professional : professionals) {
			professional.setVotouHoje(false);
		}
		
		saveRestaurants(restaurants);
		saveProfessionals(professionals);
	}
}
