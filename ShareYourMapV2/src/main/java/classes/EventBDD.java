package classes;

import java.time.LocalDateTime;

import javax.jdo.annotations.PersistenceCapable;

import com.fasterxml.jackson.annotation.JsonFormat;

@PersistenceCapable
public class EventBDD extends LocationBDD{
	private LocalDateTime start;
	private LocalDateTime end;
	
	public EventBDD(int mid, String name, String creatorName, double x, double y, String descr, String label, LocalDateTime start, LocalDateTime end) {
		super(mid, name, creatorName, x, y, descr, label);
		this.start = start;
		this.end = end;
	}
	
	public EventBDD(int mid, int id, String name, String creatorName, double x, double y, String descr, String label, LocalDateTime start, LocalDateTime end) {
		super(mid, id, name, creatorName, x, y, descr, label);
		this.start = start;
		this.end = end;
	}

	@JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
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
	
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
}
