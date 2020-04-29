package classes;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class UserBDD{
	private int id;
	private String name;
	private String password;
	private static int user_id = 1;
	
	public UserBDD(String name, String password) {
		this.id=user_id++;
		this.name = name;
		this.password = password;		
	}
	
	public UserBDD(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
