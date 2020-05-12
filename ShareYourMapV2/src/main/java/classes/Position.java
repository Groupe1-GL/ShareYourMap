package classes;

import javax.jdo.annotations.PersistenceCapable;
import com.fasterxml.jackson.annotation.JsonProperty;


@PersistenceCapable(detachable="true")
public class Position {
	private double x;
	private double y;
	
	public Position(@JsonProperty("x") double x, @JsonProperty("y") double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
