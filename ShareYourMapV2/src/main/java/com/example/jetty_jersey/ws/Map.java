package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.List;

public class Map{
	private int Map_ID;
	private String name, creatorName;
	private boolean isPublic;
	private List<Location> favorite_locations;
	private static int id = 1;
	
	public Map() {
		this.favorite_locations = new ArrayList<Location>();
		this.Map_ID = id++;
		this.name = "Map"+String.valueOf(this.Map_ID);
		this.favorite_locations = new ArrayList<Location>();
	}
	
	public static ArrayList<Map> generateMaps(){
		ArrayList<Map> maps = new ArrayList<Map>();
		for(int i=0; i<3; i++){
			Map m = new Map();
			m.setFavorites(Location.generateLocations());
			maps.add(m);
		}
		return maps;
	}
	
	
	public int getID() {
		return this.Map_ID;
	}
	
	public List<Location> getLocations() {
		return this.favorite_locations;
	}
	
	public void setFavorites(List<Location> locs) {
		this.favorite_locations = locs;
	}
	
	public boolean addLocation(Location l) {
		return this.favorite_locations.add(l);		
	}
	
	public boolean isPublic() {
		return this.isPublic;
	}
}
