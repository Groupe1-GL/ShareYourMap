package classes;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdGeneratorStrategy;

/**
 * Location is the class that represents the locations of the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
@PersistenceCapable(detachable="true")
public class Location{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private int id;
	private String name, creatorName, description, label;
	@Persistent(defaultFetchGroup="true")
	private Position position;
	@Persistent(defaultFetchGroup="true")
	private List<String> pictures;
	@Persistent(defaultFetchGroup="true")
	private List<String> messages;
	
	public Location() {
		this.name = "Location"+String.valueOf(this.id);
		this.messages = new ArrayList<String>();
		this.pictures = new ArrayList<String>();
	}
	
	public Location(String name, String creatorName, double x, double y, String descr, String label) {
		this.creatorName = creatorName;
		this.name = name;
		this.position = new Position(x,y);
		this.description = descr;
		this.label = label;
		this.messages = new ArrayList<String>();
		this.pictures = new ArrayList<String>();
	}
	
	public int getID() {
		return this.id;
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
	
	public String getLabel() {
		return this.label;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getCreatorName() {
		return this.creatorName;
	}
	
	public List<String> getPictures() {
		return pictures;
	}
	
	public List<String> getMessages() {
		return this.messages;
	}

	public boolean setName(String name) {
		this.name = name;
		return true;
	}

	public boolean setLabel(String label) {
		this.label = label;
		return true;
	}

	public boolean setDescription(String description) {
		this.description = description;
		return true;
	}

	public boolean addMessage(String msg) {
		if (msg != null) {
			this.getMessages().add(msg);
			return true;
		}
		return false;
	}
	
	public boolean addPicture(String pic) {
		if (pic != null) {
			this.getPictures().add(pic);
			return true;
		}
		else {
			return false;
		}
	}
	
	//Only for debug purposes
	public static ArrayList<Location> generateLocations(){
		ArrayList<Location> locations = new ArrayList<Location>();
		Location Monoprix = new Location("Monoprix","David",48.830211167315326, 2.3784144024565013,"Cher"," ");
		Location Carrefour = new Location("Carrefour","David",48.83211100128898, 2.362379298178112,"Proche"," ");
		locations.add(Carrefour);
		locations.add(Monoprix);
		return locations;
	}
	
	//Only for debug purposes
	public static ArrayList<Location> generateLocations2(){
		ArrayList<Location> locations = new ArrayList<Location>();
		Location Mcdo = new Location("Mcdo","David",48.826792697264764,2.378913867792432,"Filet O fish only but tasty","FastFood");
		Mcdo.addMessage("Close to college, with friendly waitresses");
		Location KFC = new Location("KFC","David",48.828830387756604, 2.3552615002566095,"Poulet"," ");
		Location Quick = new Location("Quick","David",48.87568498530489, 2.326958388281866,"Bof"," ");
		LocalDateTime s = LocalDateTime.of(2020,1,15,10,00);
		LocalDateTime e = LocalDateTime.of(2020,1,15,10,45);
		Event Cafe = new Event("Honorine","David",48.826510168645356, 2.3809641010924447,"Raspberry Cheesecake","Birthday party",s,e);
		Cafe.addMessage("Don't forget candles");
		Cafe.addMessage("Enjoy your time ^^");
		Cafe.addMessage("Happy birthday");
		locations.add(Mcdo);
		locations.add(KFC);
		locations.add(Quick);
		locations.add(Cafe);
		return locations;
	}
	
}


