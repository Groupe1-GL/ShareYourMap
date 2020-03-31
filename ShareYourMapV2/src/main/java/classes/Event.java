package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Event extends Location{
	private LocalDateTime starting;
	private LocalDateTime ending;
	
	public Event() {
		super();
	}

	public Event(String name, String creatorName, double x, double y, String descr, String label, LocalDateTime starting, LocalDateTime ending) {
		super(name, creatorName, x, y, descr, label);
		this.starting = starting;
		this.ending = ending;
	}

	public String getStarting() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return starting.format(formatter);
	}
	public void setStarting(LocalDateTime starting) {
		this.starting = starting;
	}
	public String getEnding() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return ending.format(formatter);
	}
	public void setEnding(LocalDateTime ending) {
		this.ending = ending;
	}
	
	
	

}
