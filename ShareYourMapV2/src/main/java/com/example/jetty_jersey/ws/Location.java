package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.*;

public class Location{
	private int LocID;
	private String name, creatorName, description;
	private List<String> message;//*
	private Position pos;
	private String label;
	private static int id = 1;
	//private List<image> image;
	
	public Location() {
		this.LocID = id++;
		this.name = "Location"+String.valueOf(this.LocID);
	}
	
	
	public Location(String name, String creatorName, float x, float y, String descr, String label2) {
		this.creatorName = creatorName;
		this.name = name;
		Position pos = new Position(x,y);
		this.pos = new Position(x,y);
		this.description = descr;
		this.label = label;
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


	public String getCreatorName() {
		return this.creatorName;
	}


	public boolean setName(String name) {
		if (!name.equals(null)) {
			this.name = name;
			return true;
		}
		return false;
	}


	public boolean setDescription(String descr) {
		if (!descr.equals(null)) {
			this.description = descr;
			return true;
		}
		return false;
	}


	public boolean setLabel(String label) {
		if (!label.equals(null)) {
			this.label = label;
			return true;
		}
		return false;
	}

}
