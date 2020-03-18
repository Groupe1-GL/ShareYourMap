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
	
	public List<User> getUsers() {
		return u;
	}
	
	public Response createUser( 		String name, 
										String password, 
										String cpassword, 
										String email) {
		
	}
	
	public User getUser(				int uid) {
		
	}
	public Response editUser(   		int uid, 
										String opassword, 
										String password, 
										String cpassword, 
										String email) { 
		
	}
	public boolean deleteUser(			int uid) {
		
	}
	public List<Map> getMapsOfUser(		int uid) {
		
	}
	public boolean createMap(			int uid, String name) {
		
	}
	public boolean addMapOnUser(		int uid, int mid) {
		
	}
	public boolean removeMapOnUser(		int uid, int mid) {
		
	}
	public boolean addLocationOnMap(	int uid, 
										int mid, 
										String name, 
										String descr, 
										String label, 
										float x, 
										float y) {
		
	}
	public boolean editLocation(		int uid,
										int mid,
										int lid,
										String message) {
		
	}
	public boolean contributeOnLocation(int uid,
										int mid,
										int lid,
										String message) {
		
	}
	public boolean deleteLocation(		int uid,
										int mid,
										int lid) {
		
	}

}
