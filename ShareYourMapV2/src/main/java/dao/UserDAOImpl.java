package dao;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import classes.Map;
import classes.User;

/*
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
*/

/**
 * UserDAOImpl is the implementation of the UserDAO interface used for debugging purposes.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
public class UserDAOImpl implements UserDAO {
	
	static List<User> u = User.generateUsers();
	
	public List<User> getUsers() {
		return u;
	}
	
	public Response connectUser(		String username, String password) {
		for (User us: u) {
			if (us.getName().equals(username)&&us.getPassword().equals(password)) {
				return Response.status(Response.Status.SEE_OTHER)//303
						.header(HttpHeaders.LOCATION, "/viewmap/viewmap.html?uid="+us.getId())
			            .header("X-Foo", "bar")
			            .build();
			}
		}
		 return Response
				 	.status(401)
		            .entity("Username and password do not match")
		            .build();
	}
	
	public String createUser( 		String name, 
									String password) {
		for (User us : u){
			if (us.getName().equalsIgnoreCase(name)){
				return "This username is already used.";
			}
		}
		User newUser = new User(name,password);
		u.add(newUser);
		return "viewmap/viewmap.html?uid="+newUser.getId();
	}
	
	public User getUser(int uid) {
		for (User us: u) {
			if (us.getId() == uid)
				return us;
		}
		return null;		
	}
	
	public User getUser(String username) {
		for (User us: u) {
			if (us.getName().equalsIgnoreCase(username))
				return us;
		}
		return null;		
	}
	
	public String editUser(   		int uid, 
									String password) { 
		for (User us: u) {
			if (us.getId() == uid) {
				us.setPassword(password);
				return "Password successfully updated!";
			}				
		}
		return "User not found!";
	}
	
	public boolean deleteUser(			int uid) {
		for (User us: u) {
			if (us.getId() == uid) {
				u.remove(us);
				return true;
			}
		}
		return false;		
	}
	
	public List<Map> getMapsOfUser(		int uid) {
		for (User us: u) {
			if (us.getId() == uid) {
				return us.getMaps();
			}
		}
		return null;		
	}
	
	/**
     * Adds a map on the user's map.
	 * If the map doesn't exist nothing is added.
	 * This resource method is used to add a map from the search bar.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @return	   list of maps
	 */
	public boolean addMapOnUser(		int uid, int mid) {
		for (User us: u) {
			if (us.getId() == uid) {
				for (Map ma: MapDAOImpl.m) {
					if (ma.getID() == mid) {
						us.getMaps().add(ma);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
     * Deletes a map on the user's map.
	 * If the map doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @return	   true if the operation was successful
	 */
	public boolean removeMapOnUser(		int uid, int mid) {
		for (User us: u) {
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						us.getMaps().remove(ma);
						return true;
					}
				}
			}
		}
		return false;
	}	
	
}
