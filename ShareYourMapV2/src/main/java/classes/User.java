package classes;

import java.util.List;
import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(detachable="true")
public class User{
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String name;
	private String password;
	@Persistent(defaultFetchGroup="true") @Element(dependent = "true")
	private List<Map> listMap;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.listMap = new ArrayList<Map>();		
	}
	
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
	
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	
	public boolean setName(String name) {
		if (name != null) {
			this.name = name;
			return true;
		}
		return false;
	}
	
	public void putMap(Map m) {
		listMap.add(m);
	}

	public void deleteMap(Map m) {
		listMap.remove(m);
	}
	
	public List<Map> getMaps() {
		return listMap;
	}
	
	public boolean setMaps(List<Map> m) {
		if (m != null) {
			this.listMap = m;
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

	public String getPassword() {
		return this.password;
	}
	
}
