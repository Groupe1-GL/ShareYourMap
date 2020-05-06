package dao;

import java.util.List;

import javax.ws.rs.core.Response;

import classes.Map;

/**
 * MapDAO is the interface of the Data Access Object of maps.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
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
	 * @return	the map
	 */
	public Map getMap(int mid);
	
	/**
	 * Returns a map selected by its id and his token.
	 * If there is not map matching or the access map is on private mode, 
	 * it will return a null object.
     *	 
	 * @return	the map
	 */
	public Response getSharedMap(int mid, String sharedID);
	
	/**
	 * Creates a map.
     *	 
	 * @return	the map
	 */
	public boolean createMap(int uid, String creator, boolean access);
	/**
	 * Edits a map selected by its id.
	 * If there is not map matching, it will modify nothing.
     *	 
	 * @return	true if the operation was successful
	 */
	public boolean editMap(int uid, int mid, String name, boolean access);
	/**
	 * Deletes the selected map.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	true if the operation was successful
	 */
	public boolean deleteMap(int uid, int mid);
	
}
