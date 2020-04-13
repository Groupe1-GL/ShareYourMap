package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import classes.Event;
import classes.Map;
import classes.User;

public class EventDAOImpl extends LocationDAOImpl implements EventDAO {

	static List<Event> e = new ArrayList<Event>();
	
	/**
     * Creates and adds a location on a map selected by its id.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	lid		the location identifier
	 * @param	name	the location name
	 * @param	descr	the location description
	 * @param	label	the location label
	 * @param	x		the longitude
	 * @param	y		the latitude
	 * @param   start	the event start date
	 * @param	end		the event end date
	 * @return			true if the operation was successful
	 */
	public boolean createEventOnMap(	int uid, 
										int mid, 
										String name, 
										String descr, 
										String label, 
										double x, 
										double y,
										LocalDateTime start,
										LocalDateTime end) {
		for (User us: UserDAOImpl.u) {
			if (us.getUserID() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						Event newEvent = new Event(name, us.getName(), x, y, descr, label, start, end);
						EventDAOImpl.e.add(newEvent);
						ma.getEvents().add(newEvent);
						return true;
					}
				}
			}
		}
		return false;		
	}
	
	/**
     * Gives the next events according to the current time on the current map.
	 *
	 * @param	mid		the map identifier
	 * @return			an event list
	 */
	public List<Event> nextEvents(int mid) {
		List<Event> res = new ArrayList<Event>();
		for (Map ma: MapDAOImpl.m) {
			if (ma.getID() == mid) {
				for (Event ev : ma.getEvents()) {
					if (ev.getStart().isAfter(LocalDateTime.now())) {
						res.add(ev);
					}
				}
			}
		}
		return res;
	}

}
