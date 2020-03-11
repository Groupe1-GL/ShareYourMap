package com.example.jetty_jersey.ws;

import java.util.Date;
import java.util.List;

public interface EventDAO {
	public List<Event> getEvents();
	/*
	public Date getStarting_date();
	public Date getEnding_date();
	public Date getTime();
	*/
}
