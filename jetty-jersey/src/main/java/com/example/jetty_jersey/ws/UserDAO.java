package com.example.jetty_jersey.ws;
import java.util.List;

public interface UserDAO {
	public int getUserID();
	public String getUserInfo();
	public List<Map> getMaps();  //return map's name
	public void putMap(Map m);  //Add a favorite to the map’s list
	public void deleteMap(Map m);  //Delete a favorite to the map’s list
	public Position getCurrent_Position();  // return current position
}
