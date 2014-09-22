package restoquiz.fakedatabase.jsonparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import restoquiz.model.Professional;
import restoquiz.model.Restaurant;

import com.google.gson.Gson;


public class RESToQuizJSONParser {
	
	private String restaurantsFile = "restaurants.json";
	private String professionalsFile = "professionals.json";
	private JSONParser parser;
	
	public RESToQuizJSONParser() {
		parser = new JSONParser();
	}
	
	public JSONArray getRestaurants() {
		JSONArray restaurants = null;
		try {
			restaurants = (JSONArray) parser.parse(new FileReader(restaurantsFile));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restaurants;
	}
	
	public void setRestaurants(List<Restaurant> restaurants) throws IOException {
		Gson gson = new Gson();
		String restaurantsList = gson.toJson(restaurants);
		
		FileWriter file = new FileWriter(restaurantsFile);
        try {
            file.write(restaurantsList); 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            file.flush();
            file.close();
        }
	}
	
	public JSONArray getProfessionals() {
		JSONArray professionals = null;
		try {
			professionals = (JSONArray) parser.parse(new FileReader(professionalsFile));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return professionals;
	}

	public void setProfessionals(List<Professional> professionals) throws IOException {
		Gson gson = new Gson();
		String professionalsList = gson.toJson(professionals);
		
		FileWriter file = new FileWriter(professionalsFile);
        try {
            file.write(professionalsList); 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            file.flush();
            file.close();
        }
	}
}
