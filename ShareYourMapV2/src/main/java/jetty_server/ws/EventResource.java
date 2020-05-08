package jetty_server.ws;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import classes.Event;
import classes.Location;
import dao.EventDAO;
import dao.EventDAOImpl;

/**
 * EventResource is the class of the events resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/")
public class EventResource {
	
	EventDAO eventDAO = new EventDAOImpl();

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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("maps/{map-id}/next-events")
	public List<Event> nextEvents(@PathParam("map-id") int mid) {
		return eventDAO.nextEvents(mid);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user-id}/maps/{map-id}/event/{event-id}")
	public boolean deleteEvent(@PathParam("user-id") int uid,
								  @PathParam("map-id") int mid,
								  @PathParam("event-id") int eid) {
		return eventDAO.deleteEvent(uid, mid, eid);
	}
	
}
