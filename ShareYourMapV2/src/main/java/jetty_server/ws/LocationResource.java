package jetty_server.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import classes.Event;
import classes.Location;
import dao.EventDAO;
import dao.EventDAOImpl;
import dao.LocationDAO;
import dao.LocationDAOImpl;

import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * LocationResource is the class of the locations resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/")
public class LocationResource {
	
	static LocationDAO locationDAO = new LocationDAOImpl();
	
	//voir si la r�ponse est coh�rente si l'user et/ou la map et/ou la location n'existent pas
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("location/map/{map-id}/user/{user-id}")
	public boolean createLocationOnMap(@PathParam("user-id") int uid,
									@PathParam("map-id") int mid,
									Location loc){
		String name = loc.getName();
		String descr = loc.getDescription();
		String label = loc.getLabel();
		double x = loc.getPosition().getX();
		double y = loc.getPosition().getY();
		return locationDAO.createLocationOnMap(uid, mid, name, descr, label, x, y);
	}
	
	
	//voir si la r�ponse est coh�rente si l'user et/ou la map et/ou la location n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("location/{location-id}/map/{map-id}/user/{user-id}")
	public String editLocation(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										Location loc) {
		String name = loc.getName();
		String descr = loc.getDescription();
		String label = loc.getLabel();
		if(locationDAO.editLocation(uid, mid, lid, name, descr, label)) {
			return "Successful edition";
		}
		else {
			return "Transaction BDD error";
		}
	}
	
	
	//voir si la r�ponse est coh�rente si l'user et/ou la map et/ou la location n'existent pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("location/{location-id}/map/{map-id}/user/{user-id}")
	public boolean deleteLocation(@PathParam("user-id") int uid,
								  @PathParam("map-id") int mid,
								  @PathParam("location-id") int lid) {
		return locationDAO.deleteLocation(uid, mid, lid);
	}

	
	//voir si la r�ponse est coh�rente si l'user et/ou la map et/ou la location n'existent pas
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("feed/{msg}/location/{location-id}/map/{map-id}/user/{user-id}")
	public boolean contributeOnLocation(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										@PathParam("msg") String message) {
		return locationDAO.contributeOnLocation(uid, mid, lid, message);
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("feed-img/{msg}/location/{location-id}/map/{map-id}/user/{user-id}")
	public boolean contributeOnLocationImg(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										@FormDataParam("file") InputStream uploadedInputStream,
										@FormDataParam("file") FormDataContentDisposition fileDetail) {
		return locationDAO.contributeOnLocationImg(uid, mid, lid, uploadedInputStream, fileDetail);
	}
	
	
}