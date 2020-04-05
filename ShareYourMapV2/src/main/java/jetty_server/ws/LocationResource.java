package jetty_server.ws;

import javax.ws.rs.Consumes;
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

import classes.Location;
import dao.LocationDAO;
import dao.LocationDAOImpl;

import java.util.List;

/**
 * LocationRessource is the class of the locations resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/")
public class LocationResource {
	
	LocationDAO locationDAO = new LocationDAOImpl();
	
	//voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user-id}/maps/{map-id}/location")
	public boolean createLocationOnMap(@PathParam("user-id") int uid,
									@PathParam("map-id") int mid,
									@FormParam("name") String name,
									@FormParam("description") String descr,
									@FormParam("label") String label,
									@QueryParam("x") double x,
									@QueryParam("y") double y){
		return locationDAO.createLocationOnMap(uid, mid, name, descr, label, x, y);
	}
	
	
	//voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/{location-id}/feed")
	public boolean contributeOnLocation(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										@FormParam("message") String message) {
		return locationDAO.contributeOnLocation(uid, mid, lid, message);
	}
	
	
	//voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user-id}/maps/{map-id}/location/{location-id}")
	public boolean editLocation(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										@FormParam("message") String name,
										@FormParam("description") String descr,
										@FormParam("label") String label) {
		return locationDAO.editLocation(uid, mid, lid, name, descr, label);
	}
	
	
	//voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user-id}/maps/{map-id}/location/{location-id}")
	public boolean deleteLocation(@PathParam("user-id") int uid,
								  @PathParam("map-id") int mid,
								  @PathParam("location-id") int lid) {
		return locationDAO.deleteLocation(uid, mid, lid);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/location")
	public List<Location> searchLocation(
						@DefaultValue("") @QueryParam("search") String ref) {
			return locationDAO.searchLocation(ref);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user-id}/maps/{map-id}/location")
	public List<Location> nearestLocations(@PathParam("user-id") int uid, 
										   @PathParam("map-id") int mid){
		return locationDAO.nearestLocations(uid, mid);
	}
	
}