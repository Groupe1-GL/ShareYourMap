package com.example.jetty_jersey.ws;

import java.util.List;

public interface MapDAO {
	public Map getPublicMaps(boolean access); // return all public maps
	
	/*
	//getLocations...
	public List<Location> getLocations();
	public boolean addLocation(Location l);
	*/
}
