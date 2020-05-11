package classes;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.CharacterData;
import org.passay.PasswordGenerator;

@PersistenceCapable(detachable="true")
public class Map{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private int id;	
	private String name;
	private String creatorName;
	private boolean access;
	private String sharedID;
	@Persistent(defaultFetchGroup="true") 
	private List<Location> locations;
	
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
	
	public Map(String name, String creator, boolean b) {
		this.creatorName = creator;
		this.name = name;
		this.locations = new ArrayList<Location>();
		this.access = b;
		this.sharedID = generateSharingID();
	}
	
	public static ArrayList<Map> generateMaps(){
		ArrayList <Map> maps = new ArrayList<Map>();
		Map m1 = new Map("Magasin","Emrick",true);
		m1.setLocation(Location.generateLocations());
		Map m2 = new Map("Fast Food","Emrick",false);
		m2.setLocation(Location.generateLocations2());
		maps.add(m1);
		maps.add(m2);
		return maps;
	}
	
	public static ArrayList<Map> generateMaps2(){
		ArrayList <Map> maps = new ArrayList<Map>();
		Map m1 = new Map("Shop","David",false);
		m1.setLocation(Location.generateLocations());
		Map m2 = new Map("Let's eat","David",true);
		m2.setLocation(Location.generateLocations2());
		maps.add(m1);
		maps.add(m2);
		return maps;
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
	
	public boolean getAccess() {
			return this.access;
	}
	
	public String getSharedID() {
		return sharedID;
	}

	public boolean setName(String name) {
		if (name != null) {
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
