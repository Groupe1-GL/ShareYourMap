package dao;

import java.util.List;

import classes.Event;

public interface EventDAO  extends LocationDAO{
	
	public Event getEvent();
	public List<Event> getEvents();
	
}
