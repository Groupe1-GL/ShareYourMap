package classes;

import javax.jdo.annotations.PersistenceCapable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Position is the class that represents the location positions of the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
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

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
