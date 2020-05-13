package dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import classes.Event;
import classes.Map;
import classes.User;

/**
 * EventDAOImpl is the implementation of the EventDAO interface used for debugging purposes.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
public class EventDAOImpl extends LocationDAOImpl implements EventDAO {

	static List<Event> e = new ArrayList<Event>();
	
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

	public boolean editEvent(int uid, int mid, int lid, String name, String descr, String label, LocalDateTime start,
			LocalDateTime end) {
		// TODO Auto-generated method stub
		return false;
	}
}
