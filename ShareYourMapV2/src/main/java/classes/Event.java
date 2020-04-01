package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Event extends Location{
	private LocalDateTime start;
	private LocalDateTime end;
	
	public Event(String name, String creatorName, double x, double y, String descr, String label, LocalDateTime start, LocalDateTime end) {
		super(name, creatorName, x, y, descr, label);
		this.start = start;
		this.end = end;
	}
/*
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
*/

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}


	
}
