package com.example.jetty_jersey.ws;

import java.util.List;

public interface LocationDAO {
	public List<Location> getLocation(String label);
	
	/*
	public Location getLocation();
	public boolean editLocation();  //only creator
	public boolean deleteLocation(); 
	public void putMessage(String name);
	public void deleteMessage();  //only creator
	*/
}
