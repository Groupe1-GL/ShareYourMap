package classes;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;

import java.time.LocalDateTime;
import java.util.*;

@PersistenceCapable
public class Location{
	private int LocID;
	private String name, creatorName, description;
	private List<String> message;
	private Position pos;
	private String label;
	private static int id = 1;
	//private List<image> image;
	
	public Location() {
		this.LocID = id++;
		this.name = "Location"+String.valueOf(this.LocID);
	}
	
	public Location(String name, String creatorName, double x, double y, String descr, String label) {
		this.LocID = id++;
		this.creatorName = creatorName;
		this.name = name;
		this.pos = new Position(x,y);
		this.description = descr;
		this.label = label;
	}
	
	public static ArrayList<Location> generateLocations(){
		ArrayList<Location> locations = new ArrayList<Location>();
		Location Monoprix = new Location("Monoprix","David",48.8318214,2.362692,"Cher"," ");
		Location Carrefour = new Location("Carrefour","David",48.831824,2.362692,"Proche"," ");
		locations.add(Carrefour);
		locations.add(Monoprix);
		return locations;
	}
	
	public static ArrayList<Location> generateLocations2(){
		ArrayList<Location> locations = new ArrayList<Location>();
		Location Mcdo = new Location("Mcdo","David",48.8272921,2.3780381,"Filet O fish"," ");
		Location KFC = new Location("KFC","David",48.8298797,2.3397636,"Poulet"," ");
		Location Quick = new Location("Quick","David",48.8310552,2.3172679,"Bof"," ");
		LocalDateTime s = LocalDateTime.now();
		LocalDateTime e = LocalDateTime.now().plusDays(2);
		Event Cafe = new Event("Honorine","David",48.826498,2.380959,"Chocolat","Breakfast", s,e);
		locations.add(Mcdo);
		locations.add(KFC);
		locations.add(Quick);
		locations.add(Cafe);
		return locations;
	}

	public int getID() {
		return this.LocID;
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void putDescription(String str){
		description += str;
	}
	
	public void deleteDescription(){
		description = null;
	}
	
	public List<String> getMessage(){
		return this.message;
	}
	
	public boolean putMessage(String str){
		if (!str.equals(null)) {
			this.message.add(str);//*
			return true;
		}
		else {
			return false;
		}
	}

	public void deleteMessage(){
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


	public String getName() {
		return this.name;
	}
	
	public String getEvent() {
		if(this instanceof Event) {
			return "1";
		}
		return "0";
	}

}


