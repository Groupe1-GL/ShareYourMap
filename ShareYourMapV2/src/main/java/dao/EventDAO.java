package dao;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import classes.Event;

public interface EventDAO  extends LocationDAO{
	
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
     * Removes an event on a map.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier
	 * @param	eid		the event identifier
	 * @return			true if the operation was successful
	 */
	public boolean deleteEvent(int uid, int mid, int eid);

	boolean contributeOnLocationImg(int uid, int mid, int lid, InputStream uploadedInputStream,
			FormDataContentDisposition fileDetail);
	
}
