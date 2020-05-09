package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import classes.Event;
import classes.Location;
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
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						Event newEvent = new Event(name, us.getName(), x, y, descr, label, start, end);
						EventDAOImpl.e.add(newEvent);
						ma.getLocations().add(newEvent);
						return true;
					}
				}
			}
		}
		return false;		
	}

	
	/**
     * Removes an event on a map.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier
	 * @param	eid		the event identifier
	 * @return			true if the operation was successful
	 */
	public boolean deleteEvent(int uid, int mid, int eid){
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location loc: ma.getLocations()) {
							if (loc.getID() == eid) {								
								ma.getLocations().remove(loc);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
