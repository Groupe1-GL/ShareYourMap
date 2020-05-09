package dao;
import java.util.List;

import classes.User;

/**
 * UserDAO is the interface of the Data Access Object of users.
 *
 * @author Mohamed Ahmed
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
	 * If the username is already used it don't finalize the processus.
	 * This service is used on the signup process.
     *	 
	 * @param username	a public string identifier of the user
	 * @param passwd	the password
	 * 
	 */
	public String createUser( 		String name, 
										String password);
	/**
     * Returns the user selected by his id.
	 *
	 * @param  uid the user identifier 
	 * @return	   the user object
	 */
	public User getUser(				int uid);
	
	/**
     * Returns the user selected by his name.
	 *
	 * @param  username the user identifier 
	 * @return	   the user object
	 */
	public User getUser(				String username);
	

	
	
	/**
     * Edits the user selected by his id.
	 * The username can not be changed.
	 *
	 * @param  uid 		the user identifier 
	 * @param  passwd	new password
	 * @return	   		the user object
	 */
	public String editUser(   		int uid, 
										String password);
	/**
     * Deletes the user selected by his id.
	 * A user can delete himself only.
	 *
	 * @param  uid the user identifier 
	 * @return	   true if the operation was successful
	 */
	public boolean deleteUser(			int uid);
	
	
}
