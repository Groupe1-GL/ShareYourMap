package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.*;

public class User{
	private int userID;
	private String name;
	private String password; //ADD
	private String email; //ADD
	private List<Map> listMap; 
	private Position current_position;
	private static int id = 1;
	
	public User(String name, String password, String emai) {
		this.userID=id++;
		this.name = name;
		this.password = password;
		this.email = email;
		this.listMap = new ArrayList<Map>();		
	}

	public static ArrayList<User> generateUsers(){
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0; i<3; i++) {
			User u = new User();
			u.setMaps(Map.generateMaps());
			users.add(new User());
		}
		return users;
	}

	public int getUserID() {
		return this.userID;
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
	
	
}
