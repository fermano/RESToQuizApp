
package restoquiz;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import restoquiz.fakedatabase.FakeDatabase;
import restoquiz.model.Professional;
import restoquiz.model.Restaurant;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/myresource")
public class MyResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there! ";
    }
    //votar
    @POST
    @Path("/vote/{professionalId}/{restaurantId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response vote(@PathParam("professionalId") String professionalId,
    					@PathParam("restaurantId") String restaurantId) { 

    	//fakeDb -> restaurantID: metodo de check e voto
    	//fakeDb -> professionalID: metodo de check e voto 
    	
    	FakeDatabase db = new FakeDatabase();

    	String result = db.vote(restaurantId, professionalId);

        return Response.status(201).entity(result).build(); 
    }
    
    
    @POST
    @Path("/finishDay")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response finishDay() { 
    	
    	FakeDatabase db = new FakeDatabase();
    	
    	db.finishDay();

        return Response.status(201).entity("").build(); 
    }
    
    
    @POST
    @Path("/finishWeek")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response finishWeek() { 

    	//fakeDb -> restaurantID: metodo de check e voto
    	//fakeDb -> professionalID: metodo de check e voto 
    	
    	FakeDatabase db = new FakeDatabase();
    	
    	db.finishWeek();

        return Response.status(201).entity("").build(); 
    }
    
    //chamada para zerar semana
    //chamada para encerrar votacao 
    
    @Path("/restaurants")   
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Restaurant> getRestaurants() {
    	FakeDatabase db = new FakeDatabase();
    	return db.getAvailableRestaurants();
    }
    
    @Path("/professionals")   
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Professional> getProfessionals() {
    	FakeDatabase db = new FakeDatabase();
    	return db.getProfessionals();
    } 
    
    @Path("/results")   
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Restaurant> getResults() {
    	FakeDatabase db = new FakeDatabase();
    	return db.getResults();
    } 
    
    @Path("/professional/{id}")   
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public Professional getProfessional(@PathParam("id") String professionalId) {
    	FakeDatabase db = new FakeDatabase();
    	Professional professional = db.getProfessional(professionalId);
    	return professional;
    }
    
    @Path("/restaurant/{id}")   
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public Restaurant getRestaurant(@PathParam("id") String restaurantId) {
    	FakeDatabase db = new FakeDatabase();
    	Restaurant resto = db.getRestaurant(restaurantId);
    	return resto;
    }
}
