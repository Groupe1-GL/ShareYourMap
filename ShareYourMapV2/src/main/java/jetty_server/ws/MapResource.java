package jetty_server.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import classes.Map;
import dao.MapDAO;
import dao.MapDAOImpl;

import java.util.*;

/**
 * MapRessource is the class of the maps resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/")
public class MapResource {
	
	MapDAO mapDAO = new MapDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps")
	public List<Map> getMaps(){
		return mapDAO.getMaps();
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/users/{user-id}/maps")
	public Response createMap(@PathParam("user-id") int uid,
						     @FormParam("name") String name,
						 	 @FormParam("access") int access){
		return mapDAO.createMap(uid, name, access);		
		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{map-id}")
	public Map getMap(@PathParam("map-id") int mid) {		
		return mapDAO.getMap(mid);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user-id}/maps/{map-id}")
	public boolean editMap(@PathParam("user-id") int uid,
						   @PathParam("map-id") int mid,
					       @FormParam("name") String name,
					       @FormParam("access") int access) {
		return mapDAO.editMap(uid, mid, name, access);
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user-id}/maps/{map-id}")
	public boolean deleteMap(@PathParam("user-id") int uid,
							 @PathParam("map-id") int mid) {
		return mapDAO.deleteMap(uid, mid);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mapsearch")
	public List<Map> searchMap(
			@DefaultValue("") @QueryParam("search") String ref) {
		return mapDAO.searchMap(ref);
	}
}