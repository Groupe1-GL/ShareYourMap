package classes;

import java.util.List;
import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class User{
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String name;
	private String password;
	private List<Map> listMap;
	private static int user_id = 1;
	
	public User(String name, String password) {
		this.id=user_id++;
		this.name = name;
		this.password = password;
		this.listMap = new ArrayList<Map>();		
	}
	
	public User(int id, String name, String password) {
		this.id = id;
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
	
	public void setMaps(List<Map> m) {
		this.listMap = m;
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
