package com.example.jetty_jersey.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
@Path("users/{user-id}/maps/{map-id}/location")
public class LocationResource {
	
	LocationDAO locationDAO = new LocationDAOImpl();
	
	/**
	 * Returns the list of all maps.
	 * If there is no maps, it will return a null object.
     *	 
	 * @return	the maps on the database
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map> getMaps(){
		return mapDAO.getMaps();
	}
	
}