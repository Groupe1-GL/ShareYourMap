package classes;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class MapContainer {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)//
	protected Long id = null;
	
	@Persistent
	protected List<Map> maps = null;

	public MapContainer() {
		super();
		this.maps = new ArrayList<Map>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Map> getMaps() {
		return this.maps;
	}

	public void setMaps(List<Map> maps) {
		this.maps = maps;
	}
	
}
