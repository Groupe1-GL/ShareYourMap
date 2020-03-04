package com.example.jetty_jersey.ws;

public interface LocationDAO {
	public String getLocation();  //return name of the location
	public Position getPosition();  //return position of the location
	public String getLabel();  //return label type
	public String getDescription();  //return description of the location
	public void putDescription(String str);  //add description
	public void deleteDescription();  //delete description
	public String getMessage();  //return message of the location
	public void putMessage(String name);  //add message
	public void deleteMessage();  //delete message
}
