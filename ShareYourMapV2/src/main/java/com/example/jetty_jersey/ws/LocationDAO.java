package com.example.jetty_jersey.ws;

import java.util.List;

public interface LocationDAO {
	
	//public List<Location> getLocation(String label);
	public boolean createLocationOnMap(	int uid, 
										int mid, 
										String name, 
										String descr, 
										String label, 
										float x, 
										float y);
	public boolean contributeOnLocation(int uid,
										int mid,
										int lid,
										String message);
	public boolean editLocation(		int uid,
										int mid,
										int lid,
										String message,
										String descr,
										String label);
	public boolean deleteLocation(		int uid,
										int mid,
										int lid);
	/*
	public Location getLocation();
	public boolean editLocation();  //only creator
	public boolean deleteLocation(); 
	public void putMessage(String name);
	public void deleteMessage();  //only creator
	*/
}
