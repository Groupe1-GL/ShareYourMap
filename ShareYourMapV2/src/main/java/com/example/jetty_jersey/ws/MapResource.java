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
 * MapRessource is the class of the maps resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/maps")
public class MapResource {
	
	MapDAO mapDAO = new MapDAOImpl();
	
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
	
	/**
	 * Returns a map selected by its id.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	the map
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{map-id}")
	public Map getMap(@PathParam("map-id") int mid) {
		return mapDAO.getMap(mid);
	}
	
	/**
	 * Edits a map selected by its id.
	 * If there is not map matching, it will modify nothing.
     *	 
	 * @return	true if the operation was successful
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{map-id}")
	//voir s'il y a une variable globale de session pour vérifier le current user et/ou
	// mettre un privilège de modification
	public boolean editMap(@PathParam("map-id") int mid,
					       @FormParam("name") String name) {
		return mapDAO.editMap(mid, name);
	}
	
	/**
	 * Deletes the selected map.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	true if the operation was successful
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{map-id}")
	public boolean deleteMap(@PathParam("map-id") int mid) {
		return mapDAO.deleteMap(mid);
	}
	
	/**
	 * Returns all the locations of a map selected by its id.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	the list of favorite locations
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{map-id}/location")
	public List<Location> getLocations(@PathParam("map-id") int mid) {
		return mapDAO.getLocations(mid);
	}
}