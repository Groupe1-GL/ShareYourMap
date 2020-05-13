package dao;

import classes.Map;
import java.util.List;
import javax.ws.rs.core.Response;

/**
 * MapDAO is the interface of the Data Access Object of maps.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
public interface MapDAO {
	
	/**
	 * Returns the list of all maps.
	 * If there is no maps, it will return a null object.
     *	 
	 * @return	the maps on the database
	 */
	public List<Map> getMaps();
	
	/**
	 * Returns a map selected by its id.
	 * If there is not map matching, it will return a null object.
	 *
	 * @param	mid the map identifier 
	 * @return	the map
	 */
	public Map getMap(int mid);
	
	/**
	 * Creates a map.
     *	 
     * @param	uid			the user identifier 
     * @param	creator		the creator name
     * @param	access		the type of access (public = true, private = false)
	 * @return	  			true if the operation was successful
	 */
	public boolean createMap(int uid, String creator, boolean access);
	
	/**
	 * Edits a map selected by its id.
	 * If there is not map matching, it will modify nothing.
     *	 
     * @param	uid			the user identifier 
     * @param	mid			the map identifier 
     * @param	name		the new map name
     * @param	access		the type of access (public = true, private = false)
	 * @return				true if the operation was successful
	 */	
	public boolean editMap(int uid, int mid, String name, boolean access);
	
	/**
	 * Returns a map selected by its id and his token.
	 * If there is not map matching or the access map is on private mode, 
	 * it will return a null object.
     *	 
	 * @param	uid			the user identifier 
	 * @param	mid			the map identifier 
	 * @param	sharedID 	the sharedID token
	 * @return				the map
	 */
	public Response getSharedMap(int uid, int mid, String sharedID);
	
	/**
	 * Deletes the selected map.
	 * If there is not map matching, it will return a null object.
	 * 
     * @param	uid			the user identifier 
     * @param	mid			the map identifier 
	 * @return				true if the operation was successful
	 */
	public boolean deleteMap(int uid, int mid);	
}
