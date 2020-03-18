package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.List;


public class MapDAOImpl implements MapDAO {
	
	static List<Map> m = new ArrayList<Map>();
	static List<Location> l = new ArrayList<Location>();
	
	/**
	 * Returns the list of all maps.
	 * If there is no maps, it will return a null object.
     *	 
	 * @return	the maps on the database
	 */
	public List<Map> getMaps(){
		return m;
	}
	
	/**
	 * Returns a map selected by its id.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	the map
	 */
	public Map getMap(int mid) {
		for (Map ma : m) {
			if (ma.getID() == mid) {
				return ma;
			}
		}
		return null;
	}
	
	/**
	 * Edits a map selected by its id.
	 * If there is not map matching, it will modify nothing.
     *	 
	 * @return	true if the operation was successful
	 */
	//voir s'il y a une variable globale de session pour vérifier le current user et/ou
	// mettre un privilège de modification
	public boolean editMap(int mid, String name) {
		for (Map ma : m) {
			if (ma.getID() == mid) {
				return ma.setName(name);
			}
		}
		return false;
	}
	
	/**
	 * Deletes the selected map.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	true if the operation was successful
	 */
	public boolean deleteMap(int mid) {
		for (Map ma : m) {
			if (ma.getID() == mid) {
				return m.remove(ma);
			}
		}
		return false;
	}
	
	/**
	 * Returns all the locations of a map selected by its id.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	the list of favorite locations
	 */
	public List<Location> getLocations(int mid) {
		for (Map ma : m) {
			if (ma.getID() == mid) {
				return ma.getLocations();
			}
		}
		return null;
	}	
	
	/**
	 * Creates a map.
     *	 
	 * @return	the map
	 */
	public boolean createMap(int uid, String name, int access) {
		for (User us: UserDAOImpl.u) {
			if (us.getUserID() == uid) {
				return us.getMaps().add(new Map(name, us.getName(), access==1));
			}
		}
		return false;
	}
}
