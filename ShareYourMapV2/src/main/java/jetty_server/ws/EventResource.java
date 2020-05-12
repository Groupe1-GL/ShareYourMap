package jetty_server.ws;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import classes.Event;
import classes.Location;
import dao.EventDAO;
import dao.EventDAOImpl;
import dao.datanucleus.EventDAOPersistence;

/**
 * EventResource is the class of the events resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/")
public class EventResource {
	
	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
	EventDAO eventDAO = new EventDAOPersistence(pmf);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("event/map/{map-id}/user/{user-id}")
	public boolean createLocationOnMap(@PathParam("user-id") int uid,
									@PathParam("map-id") int mid,
									Event loc){
		String name = loc.getName();
		String descr = loc.getDescription();
		String label = loc.getLabel();
		double x = loc.getPosition().getX();
		double y = loc.getPosition().getY();
		LocalDateTime start = loc.getStart();
		LocalDateTime end = loc.getEnd();
		return eventDAO.createEventOnMap(uid, mid, name, descr, label, x, y, start, end);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("event/{location-id}/map/{map-id}/user/{user-id}")
	public String editEvent(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										Event loc) {
		String name = loc.getName();
		String descr = loc.getDescription();
		String label = loc.getLabel();
		if(eventDAO.editEvent(uid, mid, lid, name, descr, label, loc.getStart(), loc.getEnd())) {
			return "Successful edition";
		}
		else {
			return "Transaction BDD error";
		}
	}
	
}
