package jetty_server.ws.bouchons;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import classes.Location;


@PersistenceCapable
public class LocationContainer {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)//
	protected Long id = null;
	
	@Persistent
	protected List<Location> locations = null;

	public LocationContainer() {
		super();
		this.locations = new ArrayList<Location>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(List<Location> Locations) {
		this.locations = locations;
	}
	
}
