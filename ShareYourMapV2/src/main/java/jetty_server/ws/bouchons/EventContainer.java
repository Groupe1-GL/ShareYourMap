package jetty_server.ws.bouchons;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import classes.Event;


@PersistenceCapable
public class EventContainer {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)//
	protected Long id = null;
	
	@Persistent
	protected List<Event> events = null;

	public EventContainer() {
		super();
		this.events = new ArrayList<Event>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> Events) {
		this.events = Events;
	}
	
}