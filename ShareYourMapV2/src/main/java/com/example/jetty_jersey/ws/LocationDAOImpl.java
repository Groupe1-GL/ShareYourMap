package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {

	static List<Location> l = new ArrayList<Location>();
	
	/**
     * Creates and adds a location on a map selected by its id.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	lid		the location identifier
	 * @param	name	the location name
	 * @param	descr 	the location description
	 * @param	label	the location label
	 * @return			true if the operation was successful
	 */
	public boolean createLocationOnMap(	int uid, 
										int mid, 
										String name, 
										String descr, 
										String label, 
										float x, 
										float y) {
		for (User us: UserDAOImpl.u) {
			if (us.getUserID() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						ma.getLocations().add(new Location(name, us.getName(), x, y, descr, label));
						return true;
					}
				}
			}
		}
		return false;
	}
}
