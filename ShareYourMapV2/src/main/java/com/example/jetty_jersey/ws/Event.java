package com.example.jetty_jersey.ws;

import java.util.Date;

public class Event extends Location{
	private Date starting_date;
	private Date ending_date;
	private Date time;
	
	public Date getStarting_date() {
		// TODO Auto-generated method stub
		return starting_date;
	}

	
	public Date getEnding_date() {
		// TODO Auto-generated method stub
		return this.ending_date;
	}

	public Date getTime() {
		// TODO Auto-generated method stub
		return this.time;
	}
	
	
}
