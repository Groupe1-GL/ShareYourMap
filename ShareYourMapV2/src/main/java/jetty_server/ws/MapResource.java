package jetty_server.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import classes.Map;
import dao.MapDAO;
import dao.MapDAOImpl;
import dao.datanucleus.MapDAOPersistence;

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
	
	//MapDAO mapDAO = new MapDAOPersistence(JDOHelper.getPersistenceManagerFactory("gl"));
	MapDAO mapDAO = new MapDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps")
	public List<Map> getMaps(){
		return mapDAO.getMaps();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{name}/user/{user-id}")
	public boolean createMap(@PathParam("user-id") int uid,
						     @PathParam("name") String name){
		return mapDAO.createMap(uid, name, false);		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{map-id}")
	public Map getMap(@PathParam("map-id") int mid) {	
			return mapDAO.getMap(mid);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/map")
	public Response getSharedMap(@QueryParam("id") int mid,
					@QueryParam("shared-id") String sharedID) {	
			return mapDAO.getSharedMap(mid, sharedID);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/maps/user/{user-id}")
	public boolean editMap(@PathParam("user-id") int uid,
					       Map m) {
		int mid =  m.getID();
		String name = m.getName();
		boolean access = m.getAccess();
		return mapDAO.editMap(uid, mid, name, access);
	}
	

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{map-id}/user/{user-id}")
	public boolean deleteMap(@PathParam("user-id") int uid,
							 @PathParam("map-id") int mid) {
		return mapDAO.deleteMap(uid, mid);
	}

}