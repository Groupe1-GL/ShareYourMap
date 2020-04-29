package classes;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.CharacterData;
import org.passay.PasswordGenerator;

@PersistenceCapable
public class Map{
	private int id;
	private String name, creatorName;
	private boolean access;
	private String sharingID;
	private List<Location> locations;
	private List<Event> events;
	private static int id_map = 1;
	
	public static String generateSharingID() {
	    PasswordGenerator gen = new PasswordGenerator();
	    CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
	    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
	    lowerCaseRule.setNumberOfCharacters(2);
	 
	    CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
	    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
	    upperCaseRule.setNumberOfCharacters(2);
	 
	    CharacterData digitChars = EnglishCharacterData.Digit;
	    CharacterRule digitRule = new CharacterRule(digitChars);
	    digitRule.setNumberOfCharacters(2);
	 
	    return gen.generatePassword(6, lowerCaseRule, 
	      upperCaseRule, digitRule);
	}
	
	public Map(String name, String creator) {
		this.locations = new ArrayList<Location>();
		this.id = id_map++;
		this.creatorName = creator;
		this.name = name;
		this.locations = new ArrayList<Location>();
		this.access = false;
		this.sharingID = generateSharingID();
	}
	
	public Map(int ID, String name, String creator, boolean b) {
		this.id = ID;
		this.name = name;
		this.locations = new ArrayList<Location>();
		this.creatorName = creator;
		this.access = b;
		this.sharingID = generateSharingID();
	}
	
	public static ArrayList<Map> generateMaps(){
		ArrayList <Map> maps = new ArrayList<Map>();
		Map m1 = new Map(1,"Magasin","David",true);
		m1.setLocation(Location.generateLocations());
		Map m2 = new Map(2,"Fast Food","David",false);
		m2.setLocation(Location.generateLocations2());
		maps.add(m1);
		maps.add(m2);
		return maps;
	}
	
	public static ArrayList<Map> generateMaps2(){
		ArrayList <Map> maps = new ArrayList<Map>();
		Map m1 = new Map(3,"Shop","Emrick",false);
		m1.setLocation(Location.generateLocations());
		Map m2 = new Map(4,"Manger","Emrick",true);
		m2.setLocation(Location.generateLocations2());
		maps.add(m1);
		maps.add(m2);
		return maps;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Location> getLocations() {
		return this.locations;
	}
	
	public void setLocation(List<Location> locs) {
		this.locations = locs;
	}
	
	public boolean addLocation(Location l) {
		return this.locations.add(l);		
	}
	
	public String getAccess() {
		if(this.access) {
			return "1";
		}
		return "0";
	}
	
	public String getSharingID() {
		return sharingID;
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
