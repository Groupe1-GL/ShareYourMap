package dao;

import java.time.LocalDateTime;

/**
 * EventDAO is the interface of the Data Access Object of events.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
public interface EventDAO extends LocationDAO{
	
	/**
     * Creates and adds an event on a map selected by its id.
	 * If the map doesn't exist nothing is added.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	name	the event name
	 * @param	descr	the event description
	 * @param	label	the event label
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
										LocalDateTime end);
	
	/**
     * Edits the selected event.
	 * If the map doesn't exist nothing is edited.
	 * 
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	lid		the event identifier
	 * @param	name	the event name
	 * @param	descr	the event description
	 * @param	label	the event label
	 * @param   start	the event start date
	 * @param	end		the event end date
	 * @return			true if the operation was successful
	 */
	public boolean editEvent(			int uid, 
										int mid, 
										int lid,
										String name, 
										String descr, 
										String label,
										LocalDateTime start,
										LocalDateTime end);	
}
