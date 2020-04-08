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
	
	public Location(int ID, String name, String creatorName, double x, double y, String descr, String label) {
		this.LocID = ID;
		this.creatorName = creatorName;
		this.name = name;
		this.pos = new Position(x,y);
		this.description = descr;
		this.label = label;
	}
	
	
	public static ArrayList<Location> generateLocations(){
		ArrayList<Location> locations = new ArrayList<Location>();
		Location Monoprix = new Location(1,"Monoprix","David",48.830211167315326, 2.3784144024565013,"Cher"," ");
		Location Carrefour = new Location(2,"Carrefour","David",48.83211100128898, 2.362379298178112,"Proche"," ");
		locations.add(Carrefour);
		locations.add(Monoprix);
		return locations;
	}
	
	public static ArrayList<Location> generateLocations2(){
		ArrayList<Location> locations = new ArrayList<Location>();
		Location Mcdo = new Location(3,"Mcdo","David",48.826792697264764,2.378913867792432,"Filet O fish"," ");
		Location KFC = new Location(4,"KFC","David",48.828830387756604, 2.3552615002566095,"Poulet"," ");
		Location Quick = new Location(5,"Quick","David",48.87568498530489, 2.326958388281866,"Bof"," ");
		LocalDateTime s = LocalDateTime.now();
		LocalDateTime e = LocalDateTime.now().plusDays(2);
		Event Cafe = new Event(6,"Honorine","David",48.826510168645356, 2.3809641010924447,"Chocolat","Breakfast", s,e);
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


