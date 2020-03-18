package com.example.jetty_jersey.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * UserRessource is the class of the User resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version 1.0
 * @since 1.0
 */
@Path("/users")
public class UserResource {
	
	static List<User> u = new ArrayList<User>();
	static List<Location> l = new ArrayList<Location>();
	
	
	/**
	 * Returns the list of all registered users.
	 * If there is not users, it will return a null object.
     *	 
	 * @return	the users on the database
	 */
	@GET
	
//	@Produces(MediaType.TEXT_HTML)
/*	public Response getUsers() {
		String str = "<ul>\n";
		//Take on the database all the entry of the user table
		for (User us : u) {
			str += "<li>" + us.getName() + "</li><br />\n";//can redirect to his user profil by get users/user-id
		}
		str += "</ul>";
		return Response
				.status(400)
				.entity(str)
				.build();
	}
*/
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
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
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(
		@FormParam("username") String name,
		@FormParam("passwd") String password,
		@FormParam("cpasswd") String cpassword,
		@FormParam("email") String email) {
		//Check if the pseudo is already present on the database to catch the code response
		//TODO with DataNucleus
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
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public User getUser(@PathParam("user-id") int uid) {
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
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{user-id}")
	public Response editUser(
		@PathParam("user-id") int uid,
		@FormParam("opasswd") String opassword,
		@FormParam("passwd") String password,
		@FormParam("cpasswd") String cpassword,
		@FormParam("email") String email) {//voir si on modifie current_position
		for (User us: u) {
			if (us.getUserID() == uid) {
				us.setName(name);
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
					boolean valid_mail = us.setEmail();//ADD setEmail regex
					if (!valid_mail) {
						return Response
								.status(400)
								.entity("The e-mail is incorrect.")
								.build();
					}
				}
				return Response
						.status(200)
						.entity("Modification successfully updated!")
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
	 * @return	   true if the operation was successfull
	 */
	 //voir si la réponse est cohérente si l'user n'existe pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public boolean deleteUser(@PathParam("user-id") int uid) {
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
	 //voir si la réponse est cohérente si l'user n'existe pas
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps")
	public List<Map> getMapsOfUser(@PathParam("user-id") int uid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				return us.getMaps();//ADD getMaps
			}
		}
		return null;
	}
	
	/**
	 * Creates a new map on the database and link it to the user.
     *	 
	 * @param  name 		public map name
	 * @param  creatorName  save the creator username
	 * @return 				true if the operation is successfull
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps")
	public boolean createMap(@PathParam("user-id") int uid,
							 @FormParam("username") String name) {
		for (User us : u) {
			if (us.getUserID() == uid) {
				return m.add(new Map(name, us.getName()));
			}
		}
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
	 //voir si la réponse est cohérente si l'user et/ou la map n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps")
	public boolean addMapOnUser(@PathParam("user-id") int uid,
								@PathParam("map-id") int mid) {
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
	
	/**
     * Deletes a map on the user's map.
	 * If the map doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @return	   true if the operation was successfull
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map n'existent pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps")
	public boolean removeMapOnUser(@PathParam("user-id") int uid,
								   @PathParam("map-id") int mid) {
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
	
	/**
     * Creates and adds a location on a map both selected by their id.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @param  lid the location identifier 
	 * @return	   true if the operation was successfull
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/{location-id}")
	public boolean addLocationOnMap(@PathParam("user-id") int uid,
									@PathParam("map-id") int mid,
									@PathParam("location-id") int lid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if (lo.getID() == lid) {
								ma.getLocations().add(lo);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
     * Contributes on the feed of a location by a message.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @param  lid the location identifier 
	 * @return	   true if the operation was successfull
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/{location-id}")
	public boolean contributeOnLocation(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										@FormParam("message") String message) {
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
	
	/**
     * Deletes a location on a map.
	 * If the map or the location doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @param  lid the location identifier 
	 * @return	   true if the operation was successfull
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/{location-id}")
	public boolean deleteLocation(@PathParam("user-id") int uid,
								  @PathParam("map-id") int mid,
								  @PathParam("location-id") int lid) {
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