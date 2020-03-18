package com.example.jetty_jersey.ws;
import java.util.List;

import javax.ws.rs.core.Response;

/**
 * UserDAO is the interface of the Data Access Object of users.
 *
 * @author Eric Gao
 * @version %I%, %G%
 * @since 1.0
 */
public interface UserDAO {
	public List<User> getUsers();
	public Response createUser( 		String name, 
										String password, 
										String cpassword, 
										String email);
	public User getUser(				int uid);
	public Response editUser(   		int uid, 
										String opassword, 
										String password, 
										String cpassword, 
										String email);
	public boolean deleteUser(			int uid);
	
	public boolean addMapOnUser(		int uid, int mid);
	public boolean removeMapOnUser(		int uid, int mid);
	
	public List<Map> getMapsOfUser(		int uid); 
	
}
