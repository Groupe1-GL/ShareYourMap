package classes;


import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class LocationBDD{
	private int id;
	private String name;
	private String creatorName;
	private int mid;
	private String description;
	private double x;
	private double y;
	private String label;
	private static int loc_id = 1;
	
	public LocationBDD(int mid, String name, String creatorName, double x, double y, String descr, String label) {
		this.mid = mid;
		this.id = loc_id++;
		this.creatorName = creatorName;
		this.name = name;
		this.x = x;
		this.y = y;
		this.description = descr;
		this.label = label;
	}
	
	public LocationBDD(int mid, int ID, String name, String creatorName, double x, double y, String descr, String label) {
		this.mid = mid;
		this.id = ID;
		this.creatorName = creatorName;
		this.name = name;
		this.x = x;
		this.y = y;
		this.description = descr;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public int getMid() {
		return mid;
	}

	public String getDescription() {
		return description;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String getLabel() {
		return label;
	}

	public static int getLoc_id() {
		return loc_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
