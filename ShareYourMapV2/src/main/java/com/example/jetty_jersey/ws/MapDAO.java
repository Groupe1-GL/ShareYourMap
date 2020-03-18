package com.example.jetty_jersey.ws;

import java.util.List;

/**
 * MapDAO is the interface of the Data Access Object of maps.
 *
 * @author Eric Gao
 * @version %I%, %G%
 * @since 1.0
 */
public interface MapDAO {
	
	public List<Map> getMaps();
	public Map getMap(int mid);
	public boolean createMap(int uid, String creator, int access);
	public boolean editMap(int mid, String name);
	public boolean deleteMap(int mid);
	public List<Location> getLocations(int mid);//inutile
	
}
