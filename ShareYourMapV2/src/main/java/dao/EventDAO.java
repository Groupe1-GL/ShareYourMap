package dao;

import java.io.InputStream;
import java.time.LocalDateTime;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public interface EventDAO extends LocationDAO{
	
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
										LocalDateTime end);
	
	/**
     * Edits the selected event.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	lid		the location identifier
	 * @param	name	the location name
	 * @param	descr	the location description
	 * @param	label	the location label
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
