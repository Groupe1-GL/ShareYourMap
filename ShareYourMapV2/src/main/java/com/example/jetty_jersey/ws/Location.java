package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.*;

public class Location{
	private int LocID;
	private String name, creator_name, description;
	private List<String> message;//*
	private Position pos;
	private String label;
	private static int id = 1;
	//private List<image> image;
	
	public Location() {
		this.LocID = id++;
		this.name = "Location"+String.valueOf(this.LocID);
	}
	
	public static ArrayList<Location> generateLocations(){
		ArrayList<Location> locations = new ArrayList<Location>();
		for(int i=0; i<3;i++) {
			locations.add(new Location());
		}
		return locations;
	}
	
	
	public int getID() {
		return this.LocID;
	}
	
	public String getLocation() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	public Position getPosition() {
		// TODO Auto-generated method stub
		return this.pos;
	}
	
	public String getLabel() {
		// TODO Auto-generated method stub
		return this.label;
	}
	
	public String getDescription(){
		// TODO Auto-generated method stub
		return this.description;
	}
	
	public void putDescription(String str){
		// TODO Auto-generated method stub
		description += str;
	}
	
	public void deleteDescription(){
		// TODO Auto-generated method stub
		description = null;
	}
	
	public List<String> getMessage(){
		// TODO Auto-generated method stub
		return this.message;
	}
	
	public boolean putMessage(String str){
		// TODO Auto-generated method stub
		if (!str.equals(null)) {
			this.message.add(str);//*
			return true;
		}
		else {
			return false;
		}
	}
	

	public void deleteMessage(){
		// TODO Auto-generated method stub
		message = null;
	}

}
