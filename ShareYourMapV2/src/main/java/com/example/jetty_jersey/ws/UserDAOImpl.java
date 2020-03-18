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
	
	public User getUser(				int uid) {
		for (User us: u) {
			if (us.getUserID() == uid)
				return us;
		}
		return null;		
	}
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
	public boolean deleteUser(			int uid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				u.remove(us);
				return true;
			}
		}
		return false;		
	}
	public List<Map> getMapsOfUser(		int uid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				return us.getMaps();//ADD getMaps
			}
		}
		return null;		
	}
	public boolean createMap(			int uid, String name) {
		for (User us : u) {
			if (us.getUserID() == uid) {
				return MapResource.m.add(new Map(name, us.getName()));
			}
		}
		return false;
	}
	public boolean addMapOnUser(		int uid, int mid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map ma: MapResource.m) {
					if (ma.getID() == mid) {
						us.getMaps().add(ma);
						return true;
					}
				}
			}
		}
		return false;
	}
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
	public boolean addLocationOnMap(	int uid, 
										int mid, 
										String name, 
										String descr, 
										String label, 
										float x, 
										float y) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						ma.getLocations().add(new Location(name, us.getName(), x, y, descr, label));
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean editLocation(		int uid,
										int mid,
										int lid,
										String message) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if (lo.getID() == lid) {								
								return lo.putMessage(message);
							}
						}
					}
				}
			}
		}
		return false;
	}
	public boolean contributeOnLocation(int uid,
										int mid,
										int lid,
										String message) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if (lo.getID() == lid) {								
								return lo.putMessage(message);
							}
						}
					}
				}
			}
		}
		return false;
	}
	public boolean deleteLocation(		int uid,
										int mid,
										int lid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if (lo.getID() == lid) {								
								ma.getLocations().remove(lo);
								//gérer l'instruction : supprimer de la base de la donnée
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
