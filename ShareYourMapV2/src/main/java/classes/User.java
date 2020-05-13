package classes;

import java.util.List;
import java.util.ArrayList;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdGeneratorStrategy;

/**
 * User is the class that represents the users of the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
@PersistenceCapable(detachable="true")
public class User{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
	private int id;
	
	private String name,password;
	
	@Persistent(defaultFetchGroup="true") 
	private List<Map> maps;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.maps = new ArrayList<Map>();		
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Map> getMaps() {
		return maps;
	}	

	public String getPassword() {
		return this.password;
	}
	
	public boolean setName(String name) {
		if (name != null) {
			this.name = name;
			return true;
		}
		return false;
	}
	
	public boolean setMaps(List<Map> m) {
		if (m != null) {
			this.maps = m;
			return true;
		}
		return false;
	}

	public boolean setPassword(String password) {
		if (password != null) {
			this.password = password;
			return true;
		}
		return false;
	}
	
	//Only for debug purposes
	public static ArrayList<User> generateUsers() {
		ArrayList<User> users = new ArrayList<User>();
		User u1 = new User("David","ddd");
		u1.setMaps(Map.generateMaps2());
		User u2 = new User("Emrick","ss");
		u2.setMaps(Map.generateMaps());
		users.add(u1);
		users.add(u2);
		return users;
	}
}
