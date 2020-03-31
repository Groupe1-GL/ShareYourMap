package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

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
	public Response createMap(int uid, String name, int access) {
		for (User us: UserDAOImpl.u) {
			if (us.getUserID() == uid) {
				Map newMap = new Map(name, us.getName(), access==1);
				MapDAOImpl.m.add(newMap);
				return Response.status(Response.Status.SEE_OTHER)
			            .header(HttpHeaders.LOCATION, "/viewmap/viewmap.html")
			            .header("X-Foo", "bar")
			            .build();
			}
		}
		return Response
			 	.status(402)
	            .entity("Unvalid creation")
	            .build();
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
			if (us.getUserID() == uid) {
				for (Map ma : m) {
					if ((ma.getID() == mid)&&(uid == us.getUserID())) {
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
			if (us.getUserID() == uid) {
				for (Map ma : m) {
					if ((ma.getID() == mid)&&(ma.getCreatorName() == us.getName())) {
						return m.remove(ma);
					}
				}
			}
		}
		return false;
	}
	
	/**
     * Returns the list of maps matching with the keywords.
	 * If the map doesn't exist or no map matches  a null object is returned.
	 * 
	 * @param  value	keywords where map name can match
	 * @return	   		list of maps
	 */
	public List<Map> searchMap(String ref) {
		List<Map> res = new ArrayList<Map>();
		Pattern pattern = Pattern.compile(ref);
		Matcher matcherName;
		for (Map ma: m) {
			matcherName = pattern.matcher(ma.getName());
			while (matcherName.find()&&(!res.contains(ma))) {
				res.add(ma);
			}
		}
		return res;
	}
	
	
	
	// **********
	
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
	
}
