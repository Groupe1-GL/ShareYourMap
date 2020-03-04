package com.example.jetty_jersey.ws;

import java.util.Date;

public class Event extends Location implements EventDAO{
	private Date starting_date;
	private Date ending_date;
	private Date time;
	
	@Override
	public Date getStarting_date() {
		// TODO Auto-generated method stub
		return starting_date;
	}
	@Override
	public Date getEnding_date() {
		// TODO Auto-generated method stub
		return this.ending_date;
	}
	@Override
	public Date getTime() {
		// TODO Auto-generated method stub
		return this.time;
	}
	
	
}
