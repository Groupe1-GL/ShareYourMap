package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.List;

public class Map implements MapDAO{
	private int Map_ID;
	private String name, creatorName;
	private boolean access_type;
	private List<Location> favorite_locations;
	private static int id = 1;
	
	public Map() {
		this.favorite_locations = new ArrayList<Location>();
		this.Map_ID = id++;
		this.name = "Map"+String.valueOf(this.Map_ID);
	}
	
	public int getID() {
		return this.Map_ID;
	}
	
	@Override
	public List<Location> getLocations() {
		return this.favorite_locations;
	}
	
	@Override
	public boolean addLocation(Location l) {
		return this.favorite_locations.add(l);		
	}
}
