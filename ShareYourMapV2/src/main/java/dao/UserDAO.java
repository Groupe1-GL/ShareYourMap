package dao;
import java.util.List;

import javax.ws.rs.core.Response;

import classes.Map;
import classes.User;

/**
 * UserDAO is the interface of the Data Access Object of users.
 *
 * @author Eric Gao
 * @version %I%, %G%
 * @since 1.0
 */
public interface UserDAO {
	
	/**
	 * Returns the list of all registered users.
	 * If there is not users, it will return a null object.
     *	 
	 * @return	the users on the database
	 */
	public List<User> getUsers();
	/**
	 * Creates a new user on the database according to the user's information matching the fields' names.
	 * If the username is already used it don't finalize the processus and returns a BAD_REQUEST HTTP code.
	 * This service is used on the signup process.
     *	 
	 * @param username	a public string identifier of the user
	 * @param passwd	the password
	 * @param cpasswd	confirmation of the previous password entry
	 * 
	 */
	public Response createUser( 		String name, 
										String password, 
										String cpassword);
	/**
     * Returns the user selected by his id.
	 *
	 * @param  uid the user identifier 
	 * @return	   the user object
	 */
	public User getUser(				int uid);
	/**
     * Edits the user selected by his id.
	 * The username can not be changed.
	 *
	 * @param  uid 		the user identifier 
	 * @param  opasswd 	the user's current password
	 * @param  passwd	new password
	 * @param  cpasswd  confirmation of the new password to avoid mistake
	 * @return	   		the user object
	 */
	public Response editUser(   		int uid, 
										String opassword, 
										String password, 
										String cpassword);
	/**
     * Deletes the user selected by his id.
	 * A user can delete himself only.
	 *
	 * @param  uid the user identifier 
	 * @return	   true if the operation was successful
	 */
	public boolean deleteUser(			int uid);
	/**
     * Returns a list of the user's maps selected by his id.
	 * If the user doesn't exist a null object is returned.
	 * This resource method is used in order to display the list of the user's maps 
	 * on the web application.
	 *
	 * @param  uid the user identifier 
	 * @return	   list of maps
	 */
	public List<Map> getMapsOfUser(		int uid); 
	/**
     * Adds a map on the user's map.
	 * If the map doesn't exist nothing is added.
	 * This resource method is used to add a map from the search bar.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @return	   list of maps
	 */
	public boolean addMapOnUser(		int uid, int mid);
	/**
     * Deletes a map on the user's map.
	 * If the map doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @return	   true if the operation was successful
	 */
	public boolean removeMapOnUser(		int uid, int mid);
	
	/**
     * Connect a user on the website and redirect it to his map page.
	 *
	 * @param	username 			the user identifier 
	 * @param	password	the user's password 
	 * @return	   			a text that describe the response
	 */
	public Response connectUser(		String username, String password);
	
	
}
