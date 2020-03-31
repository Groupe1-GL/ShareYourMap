package classes;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;

import java.util.*;

@PersistenceCapable
public class User{
	private int userID;
	private String name;
	private String password;
	private List<Map> listMap; 
	private Position currentPosition;
	private static int id = 1;
	
	public User(String name, String password) {
		this.userID=id++;
		this.name = name;
		this.password = password;
		this.listMap = new ArrayList<Map>();		
	}
	
	public static User getUser() {
		User u1 = new User("Paul","ddd");
		u1.setMaps(Map.generateMaps2());
		return u1;
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
	
	public int getUserID() {
		return this.userID;
	}
	public String getName() {
		return this.name;
	}
	
	public boolean setName(String name) {
		if (!name.equals(null)) {
			this.name = name;
			return true;
		}
		return false;
	}
	
	public String getUserInfo() {
		return "ID: "+String.valueOf(this.userID)+" Username: "+this.name;
	}
	
	public void putMap(Map m) {
		listMap.add(m);
	}

	public void deleteMap(Map m) {
		listMap.remove(m);
	}

	public Position getCurrentPosition() {
		return this.currentPosition;
	}
	
	public List<Map> getMaps() {
		return this.listMap;
	}
	
	public void setMaps(List<Map> m) {
		this.listMap = m;
	}

	public boolean setPassword(String password2) {
		if (!password2.equals(null)) {
			this.password = password2;
			return true;
		}
		return false;
	}

	public String getPassword() {
		return this.password;
	}
	
}
