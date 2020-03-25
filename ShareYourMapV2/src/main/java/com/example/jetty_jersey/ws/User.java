package com.example.jetty_jersey.ws;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;

import java.util.*;

@PersistenceCapable
public class User{
	private int userID;
	private String name;
	private String password; //ADD
	private String email; //ADD
	private List<Map> listMap; 
	private Position current_position;
	private static int id = 1;
	
	public User(String name, String password, String email) {
		this.userID=id++;
		this.name = name;
		this.password = password;
		this.email = email;
		this.listMap = new ArrayList<Map>();		
	}
	
	public static User getUser() {
		User u1 = new User("David","ddd","deeefe@gmail.com");
		u1.setMaps(Map.generateMaps());
		return u1;
	}
	
	public static ArrayList<User> generateUsers() {
		ArrayList<User> users = new ArrayList<User>();
		User u1 = new User("David","ddd","deeefe@gmail.com");
		u1.setMaps(Map.generateMaps());
		User u2 = new User("Emrick","ss","ssafe@gmail.com");
		u2.setMaps(Map.generateMap2());
		users.add(u1);
		users.add(u2);
		return users;
	}
	
	///
	
	public static void init() {
		User u1 = new User("David","123","david@mail.com");
		Map m1 = new Map("Magasins","David", true);
		u1.getMaps().add(m1);
		Location l1 = new Location("Monoprix","David",21,32,"descr","label");
		m1.getLocations().add(l1);
		
		//add on db
		UserDAOImpl.u.add(u1);
		MapDAOImpl.m.add(m1);
		LocationDAOImpl.l.add(l1);
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
		// TODO Auto-generated method stub
		listMap.add(m);
	}

	public void deleteMap(Map m) {
		// TODO Auto-generated method stub
		listMap.remove(m);
	}

	public Position getCurrent_Position() {
		// TODO Auto-generated method stub
		return this.current_position;
	}
	
	public List<Map> getMaps() {
		// TODO Auto-generated method stub
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

	public boolean setEmail(String email) {
		if (!email.equals(null)) {
			this.email = email;
			return true;
		}
		return false;
	}
}
