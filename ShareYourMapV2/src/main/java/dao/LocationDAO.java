package dao;

import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition; 

/**
 * LocationDAO is the interface of the Data Access Object of locations.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
public interface LocationDAO {	
	
	/**
     * Creates and adds a location on a map selected by its id.
	 * If the map doesn't exist nothing is added.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	name	the location name
	 * @param	descr 	the location description
	 * @param	label	the location label
	 * @param	x		the longitude
	 * @param	y		the latitude
	 * @return			true if the operation was successful
	 */
	public boolean createLocationOnMap(	int uid, 
										int mid, 
										String name, 
										String descr, 
										String label, 
										double x, 
										double y);
	
	/**
     * Edits a location.
     * If the map or the location doesn't exist nothing is edited.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid 	the map identifier 
	 * @param	lid 	the location identifier 
	 * @param	name 	the location name
	 * @param	descr	the location description
	 * @param	label	the location label
	 * @return			true if the operation was successful
	 */
	public boolean editLocation(		int uid,
										int mid,
										int lid,
										String name,
										String descr,
										String label);
	
	/**
     * Contributes on the feed of a location by a message.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param  		uid		the user identifier 
	 * @param  		mid		the map identifier 
	 * @param  		lid		the location identifier 
	 * @param		message
	 * @return				true if the operation was successful
	 */
	public boolean contributeOnLocation(int uid,
										int mid,
										int lid,
										String message);
										
	/**
     * Contributes on the feed of a location by a picture.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param  		uid					the user identifier 
	 * @param  		mid					the map identifier 
	 * @param  		lid					the location identifier 
	 * @param		uploadedInputStream	the picture data
	 * @return		true				if the operation was successful
	 */
	public boolean contributeOnLocationImg( int uid,
											int mid,
											int lid,
											InputStream uploadedInputStream,
											FormDataContentDisposition fileDetail);
	
	/**
     * Deletes a location on a map.
	 * If the map or the location doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @param  lid the location identifier 
	 * @return	   true if the operation was successful
	 */
	public boolean deleteLocation(		int uid,
										int mid,
										int lid);	
}
