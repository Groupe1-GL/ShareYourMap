package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.*;

public class User implements UserDAO{
	private int User_ID;
	private String name;
	private List<Map> listMap; 
	private Position current_position;
	private static int id = 1;
	
	public User() {
		this.User_ID=id++;
		this.name = "user"+String.valueOf(this.User_ID);
	}
	
	@Override
	public int getUserID() {
		return this.User_ID;
	}
	
	@Override
	public String getUserInfo() {
		return "ID: "+String.valueOf(this.User_ID)+" Username: "+this.name;
	}
	
	@Override
	public void putMap(Map m) {
		// TODO Auto-generated method stub
		listMap.add(m);
	}
	@Override
	public void deleteMap(Map m) {
		// TODO Auto-generated method stub
		listMap.remove(m);
	}
	@Override
	public Position getCurrent_Position() {
		// TODO Auto-generated method stub
		return this.current_position;
	}
	
	@Override
	public List<Map> getMaps() {
		// TODO Auto-generated method stub
		return this.listMap;
	}
	
	
}
