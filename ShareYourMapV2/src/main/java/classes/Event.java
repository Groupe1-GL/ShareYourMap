package classes;

import java.time.LocalDateTime;
import javax.jdo.annotations.PersistenceCapable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@PersistenceCapable(detachable="true")
public class Event extends Location{
	
	private LocalDateTime start;
	private LocalDateTime end;
	
	public Event(@JsonProperty("name") String name,@JsonProperty("creatorName") String creatorName,@JsonProperty("x") double x,@JsonProperty("y") double y,@JsonProperty("descr") String descr,@JsonProperty("label") String label,@JsonProperty("start") String start, @JsonProperty("end") String end) {
		super(name, creatorName, x, y, descr, label);
		String[] decompStart = start.split("-");
		String[] decompEnd = end.split("-");
		this.start = LocalDateTime.of(Integer.parseInt(decompStart[0]), Integer.parseInt(decompStart[1]), Integer.parseInt(decompStart[2]), Integer.parseInt(decompStart[3]), Integer.parseInt(decompStart[4]));
		this.end = LocalDateTime.of(Integer.parseInt(decompEnd[0]), Integer.parseInt(decompEnd[1]), Integer.parseInt(decompEnd[2]), Integer.parseInt(decompEnd[3]), Integer.parseInt(decompEnd[4]));
	}
	
	public Event(String name, String creatorName, double x, double y, String descr, String label, LocalDateTime start, LocalDateTime end) {
		super(name, creatorName, x, y, descr, label);
		this.start = start;
		this.end = end;
	}
	/*
	public Event(int ID, String name, String creatorName, double x, double y, String descr, String label, LocalDateTime start, LocalDateTime end) {
		super(ID,name, creatorName, x, y, descr, label);
		this.start = start;
		this.end = end;
	}*/

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
