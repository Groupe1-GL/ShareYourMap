package jetty_server.ws;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
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
import dao.datanucleus.MapDAOPersistence;

/**
 * MapRessource is the class of the maps resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
@Path("/")
public class MapResource {
	
	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
	MapDAO mapDAO = new MapDAOPersistence(pmf);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps")
	public List<Map> getMaps(){
		return mapDAO.getMaps();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{map-id}")
	public Map getMap(@PathParam("map-id") int mid) {	
			return mapDAO.getMap(mid);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{name}/user/{user-id}")
	public boolean createMap(@PathParam("user-id") int uid,
						     @PathParam("name") String name){
		return mapDAO.createMap(uid, name, false);		
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/map")
	public Response getSharedMap(@QueryParam("id") int mid,
					@QueryParam("shared-id") String sharedID,
					@QueryParam("uid") int uid) {	
			return mapDAO.getSharedMap(uid, mid, sharedID);
	}	

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{map-id}/user/{user-id}")
	public boolean deleteMap(@PathParam("user-id") int uid,
							 @PathParam("map-id") int mid) {
		return mapDAO.deleteMap(uid, mid);
	}

}