package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.*;

public class Location implements LocationDAO{
	private int Loc_ID;
	private String name, creator_name, description, message;
	private Position pos;
	private String label;
	private static int id = 1;
	//private List<image> image;
	
	public Location() {
		this.Loc_ID = id++;
		this.name = "Location"+String.valueOf(this.Loc_ID);
	}
	
	public int getID() {
		return this.Loc_ID;
	}
	
	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return this.pos;
	}
	
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return this.label;
	}
	
	@Override
	public String getDescription(){
		// TODO Auto-generated method stub
		return this.description;
	}
	
	@Override
	public void putDescription(String str){
		// TODO Auto-generated method stub
		description += str;
	}
	
	@Override
	public void deleteDescription(){
		// TODO Auto-generated method stub
		description = null;
	}
	
	@Override
	public String getMessage(){
		// TODO Auto-generated method stub
		return this.message;
	}
	
	@Override
	public void putMessage(String str){
		// TODO Auto-generated method stub
		message += str;
	}
	
	@Override
	public void deleteMessage(){
		// TODO Auto-generated method stub
		message = null;
	}

}
