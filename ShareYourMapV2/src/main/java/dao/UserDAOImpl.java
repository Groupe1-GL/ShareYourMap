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
 * UserDAOImpl is the implementation of the UserDAO interface.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
public class UserDAOImpl implements UserDAO {
	
	static List<User> u = User.generateUsers();
	
	/**
	 * Returns the list of all registered users.
	 * If there is not users, it will return a null object.
     *	 
	 * @return	the users on the database
	 */
	public List<User> getUsers() {
		return u;
	}
	
	
	/**
     * Connect a user on the website and redirect it to his map page.
	 *
	 * @param	username 			the username 
	 * @param	password	the user's password 
	 * @return	   			a text that describe the response
	 */
	public Response connectUser(		String username, String password) {
		for (User us: u) {
			if (us.getName().equals(username)&&us.getPassword().equals(password)) {
				return Response.status(Response.Status.SEE_OTHER)
			            .header(HttpHeaders.LOCATION, "/viewmap/viewmap.html")
			            .header("X-Foo", "bar")
			            .build();
			}
		}
		 return Response
				 	.status(402)
		            .entity("Username and password do not match")
		            .build();
	}
	
	/**
	 * Creates a new user on the database according to the user's information matching the fields' names.
	 * If the username is already used it don't finalize the processus and returns a BAD_REQUEST HTTP code.
	 * This service is used on the signup process.
     *	 
	 * @param username	a public string identifier of the user
	 * @param passwd	the password
	 * @param cpasswd	confirmation of the previous password entry
	 * @return			a text that describe the response
	 */
	public Response createUser( 		String name, 
										String password, 
										String cpassword) {
		for (User us : u){
			if (us.getName().equals(name)){
				return Response
						.status(400)
						.entity("This username is already used.")
						.build();
			}
		}
		//Check if the passwords are equals
		if (password.equals(cpassword)){
			//Encrypt & Insert into the database
			u.add(new User(name,password));
			return Response
					.status(201)
					.entity("You've been successfully signed up.")
					.build();
		}
		else {
			return Response
					.status(400)
					.entity("The passwords do not match.")
					.build();
		}		
	}
	
	/**
     * Returns the user selected by his id.
	 *
	 * @param  uid the user identifier 
	 * @return	   the user object
	 */
	public User getUser(int uid) {
		for (User us: u) {
			if (us.getUserID() == uid)
				return us;
		}
		return null;		
	}
	
	/**
     * Edits the user selected by his id.
	 * The username can not be changed.
	 *
	 * @param  uid 		the user identifier 
	 * @param  opasswd 	the user's current password
	 * @param  passwd	new password
	 * @param  cpasswd  confirmation of the new password to avoid mistake
	 * @return	   		a text that describe the response
	 */
	public Response editUser(   		int uid, 
										String opassword, 
										String password, 
										String cpassword) { 
		for (User us: u) {
			if (us.getUserID() == uid) {
				if (!opassword.equals(null)&&!password.equals(null)&&!cpassword.equals(null)){
					if (opassword.equals(us.getPassword())&&password.equals(cpassword)){//ADD getPassword
						us.setPassword(password);//ADD setPassword encryption
					}
					else {
						return Response
								.status(400)
								.entity("The passwords do not match.")
								.build();
					}
				}
				return Response
						.status(200)
						.entity("Password successfully updated!")
						.build();
			}					
		}
		return Response
					.status(404)
					.entity("User not found!")
					.build();		
	}
	
	/**
     * Deletes the user selected by his id.
	 * A user can delete himself only.
	 *
	 * @param  uid the user identifier 
	 * @return	   true if the operation was successful
	 */
	public boolean deleteUser(			int uid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				u.remove(us);
				return true;
			}
		}
		return false;		
	}
	
	/**
     * Returns a list of the user's maps selected by his id.
	 * If the user doesn't exist a null object is returned.
	 * This resource method is used in order to display the list of the user's maps 
	 * on the web application.
	 *
	 * @param  uid the user identifier 
	 * @return	   list of maps
	 */
	public List<Map> getMapsOfUser(		int uid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
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
			if (us.getUserID() == uid) {
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
			if (us.getUserID() == uid) {
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
