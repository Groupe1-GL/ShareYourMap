package com.example.jetty_jersey.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * LocationRessource is the class of the locations resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/users/{user-id}/maps/{map-id}/location/")
public class LocationResource {
	
	LocationDAO locationDAO = new LocationDAOImpl();
	
	/**
     * Creates and adds a location on a map selected by its identifier.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	lid		the location identifier
	 * @param	name	the location name
	 * @param	descr 	the location description
	 * @param	label	the location label
	 * @param	x		the latitude position
	 * @param	y		the longitude position
	 * @return			true if the operation was successful
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/")
	public boolean createLocationOnMap(@PathParam("user-id") int uid,
									@PathParam("map-id") int mid,
									@FormParam("name") String name,
									@FormParam("description") String descr,
									@FormParam("label") String label,
									@QueryParam("x") float x,
									@QueryParam("y") float y){
		return locationDAO.createLocationOnMap(uid, mid, name, descr, label, x, y);
	}
	
}