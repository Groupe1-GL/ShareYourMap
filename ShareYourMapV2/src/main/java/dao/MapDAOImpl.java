package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.Location;
import classes.Map;
import classes.User;


public class MapDAOImpl implements MapDAO {
	
	static List<Map> m = Map.generateMaps2();

	/**
	 * Returns the list of all maps.
	 * If there is no maps, it will return a null object.
     *	 
	 * @return	the maps on the database
	 */
	public List<Map> getMaps(){
		return MapDAOImpl.m;
	}
	
	/**
	 * Returns a map selected by its id.
	 * If there is not map matching, it will return a null object.
     *	 
	 * @return	the map
	 */
	public Map getMap(int mid) {
		for (Map ma : MapDAOImpl.m) {
			if (ma.getID() == mid) {
				return ma;
			}
		}
		return null;
	}
	
	/**
	 * Creates a map.
     *	 
	 * @return	the map
	 */
	public boolean createMap(int uid, String name) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				Map newMap = new Map(name, us.getName());
				MapDAOImpl.m.add(newMap);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Edits a map selected by its id.
	 * If there is not map matching, it will modify nothing.
     *	 
	 * @return	true if the operation was successful
	 */
	//voir s'il y a une variable globale de session pour vérifier le current user et/ou
	// mettre un privilège de modification
	public boolean editMap(int uid, int mid, String name, int access) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma : m) {
					if ((ma.getID() == mid)&&(uid == us.getId())) {
						return ma.setName(name)&&ma.setAccess(access==1);
					}
				}
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
	public boolean deleteMap(int uid, int mid) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma : m) {
					if ((ma.getID() == mid)&&(ma.getCreatorName() == us.getName())) {
						return m.remove(ma);
					}
				}
			}
		}
		return false;
	}
}
