package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Map{
	private int Map_ID;
	private String name, creatorName;
	private boolean access;
	private List<Location> favoriteLocations;
	private static int id = 1;
	
	public Map(String name, String creator) {
		this.favoriteLocations = new ArrayList<Location>();
		this.Map_ID = id++;
		this.creatorName = creator;
		this.name = name;
		this.favoriteLocations = new ArrayList<Location>();
	}
	
	public Map(String name, String creator, boolean b) {
		this.name = name;
		this.favoriteLocations = new ArrayList<Location>();
		this.creatorName = creator;
		this.access = b;
	}
	
	public static ArrayList<Map> generateMaps(){
		ArrayList <Map> maps = new ArrayList<Map>();
		Map m1 = new Map("Magasin","David");
		m1.setLocation(Location.generateLocations());
		Map m2 = new Map("Fast Food","David");
		m2.setLocation(Location.generateLocations2());
		maps.add(m1);
		maps.add(m2);
		return maps;
	}
	
	public static ArrayList<Map> generateMap2(){
		ArrayList <Map> maps = new ArrayList<Map>();
		Map m1 = new Map("Shop","Emrick");
		m1.setLocation(Location.generateLocations());
		Map m2 = new Map("Bouffe","Emrick");
		m2.setLocation(Location.generateLocations2());
		maps.add(m1);
		maps.add(m2);
		return maps;
	}

	public int getID() {
		return this.Map_ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Location> getLocations() {
		return this.favoriteLocations;
	}
	
	public void setLocation(List<Location> locs) {
		this.favoriteLocations = locs;
	}
	
	public boolean addLocation(Location l) {
		return this.favoriteLocations.add(l);		
	}
	
	public boolean isPublic() {
		return this.access;
	}
	
	public boolean setName(String name) {
		if (!name.equals(null)) {
			this.name= name;
			return true;
		}
		return false;
	}

	public boolean setAccess(boolean b) {
		if (b||!b) {//if b is boolean
			this.access = b;
			return true;
		}
		return false;
	}

	public String getCreatorName() {
		return this.creatorName;
	}
}
