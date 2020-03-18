package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

/**
 * UserDAOImpl is the implementation of the UserDAO interface.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
public class UserDAOImpl implements UserDAO {
	
	static List<User> u = new ArrayList<User>();
	
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
	 * Creates a new user on the database according to the user's information matching the fields' names.
	 * If the username is already used it don't finalize the processus and returns a BAD_REQUEST HTTP code.
	 * This service is used on the signup process.
     *	 
	 * @param username	a public string identifier of the user
	 * @param passwd	the password
	 * @param cpasswd	confirmation of the previous password entry
	 * @param email		the email of the user
	 */
	public Response createUser( 		String name, 
										String password, 
										String cpassword, 
										String email) {
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
			u.add(new User(name,password,email));//gérer regex d'email & password encryption
			return Response
					.status(201)
					.entity("You've been successfuly signed up.")
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
	public User getUser(				int uid) {
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
	 * @param  email	email of the user
	 * @return	   		the user object
	 */
	public Response editUser(   		int uid, 
										String opassword, 
										String password, 
										String cpassword, 
										String email) { 
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
				if (!email.equals(null)) {
					boolean valid_mail = us.setEmail(email);//ADD setEmail regex
					if (!valid_mail) {
						return Response
								.status(400)
								.entity("The e-mail is incorrect.")
								.build();
					}
				}
				return Response
						.status(200)
						.entity("Modification successfuly updated!")
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
