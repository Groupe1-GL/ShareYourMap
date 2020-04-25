package classes;

import java.time.LocalDateTime;
import javax.jdo.annotations.PersistenceCapable;

import com.fasterxml.jackson.annotation.JsonFormat;

@PersistenceCapable(detachable="true")
public class Event extends Location{
	private LocalDateTime start;
	private LocalDateTime end;
	
	public Event(String name, String creatorName, double x, double y, String descr, String label, LocalDateTime start, LocalDateTime end) {
		super(name, creatorName, x, y, descr, label);
		this.start = start;
		this.end = end;
	}
	
	public Event(int ID, String name, String creatorName, double x, double y, String descr, String label, LocalDateTime start, LocalDateTime end) {
		super(ID,name, creatorName, x, y, descr, label);
		this.start = start;
		this.end = end;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	
	@JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	public LocalDateTime getEnd() {
		return end;
	}
	
	@JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}


	
}
