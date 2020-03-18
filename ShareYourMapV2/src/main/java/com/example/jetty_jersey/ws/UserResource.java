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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * UserRessource is the class of the User resource used in the ShareYourMap website.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/users")
public class UserResource {
	
	UserDAO userDAO = new UserDAOImpl();
	
	/**
	 * Returns the list of all registered users.
	 * If there is not users, it will return a null object.
     *	 
	 * @return	the users on the database
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
		return userDAO.getUsers();
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
		return userDAO.createUser(name, password, cpassword, email);
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
		return userDAO.getUser(uid);
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
		return userDAO.editUser(uid, opassword, password, cpassword, email);
	}
	
	/**
     * Deletes the user selected by his id.
	 * A user can delete himself only.
	 *
	 * @param  uid the user identifier 
	 * @return	   true if the operation was successful
	 */
	 //voir si la réponse est cohérente si l'user n'existe pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public boolean deleteUser(@PathParam("user-id") int uid) {
		return userDAO.deleteUser(uid);
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
	//voir si inutile, mais permet d'encapsuler et protéger credentials
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps")
	public List<Map> getMapsOfUser(@PathParam("user-id") int uid) {
		return userDAO.getMapsOfUser(uid);
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
	@Path("/{user-id}/maps/{map-id}")
	public boolean addMapOnUser(@PathParam("user-id") int uid,
								@PathParam("map-id") int mid) {
		return userDAO.addMapOnUser(uid, mid);
	}
	
	/**
     * Deletes a map on the user's map.
	 * If the map doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @return	   true if the operation was successful
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map n'existent pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/{map-id}")
	public boolean removeMapOnUser(@PathParam("user-id") int uid,
								   @PathParam("map-id") int mid) {
		return userDAO.removeMapOnUser(uid, mid);
	}
	
	/**
     * Creates and adds a location on a map selected by its identifier.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	lid		the location identifier
	 * @param	name	the location name
	 * @param	descr 	the location description
	 * @param	label	the location label
	 * @param	x		the latitude position
	 * @param	y		the longitude position
	 * @return			true if the operation was successful
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/")
	public boolean createLocationOnMap(@PathParam("user-id") int uid,
									@PathParam("map-id") int mid,
									@FormParam("name") String name,
									@FormParam("description") String descr,
									@FormParam("label") String label,
									@QueryParam("x") float x,
									@QueryParam("y") float y){
		return userDAO.createLocationOnMap(uid, mid, name, descr, label, x, y);
	}
	
	/**
     * Contributes on the feed of a location by a message.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @param  lid the location identifier 
	 * @return	   true if the operation was successful
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/{location-id}")
	public boolean contributeOnLocation(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										@FormParam("message") String message) {
		return userDAO.contributeOnLocation(uid, mid, lid, message);
	}
	
	/**
     * Edits a location.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid 	the map identifier 
	 * @param	lid 	the location identifier 
	 * @param	name 	the location name
	 * @param	descr	the location description
	 * @param	label	the location label
	 * @return			true if the operation was successful
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/{location-id}")
	public boolean editLocation(@PathParam("user-id") int uid,
										@PathParam("map-id") int mid,
										@PathParam("location-id") int lid,
										@FormParam("message") String name,
										@FormParam("description") String descr,
										@FormParam("label") String label) {
		return userDAO.editLocation(uid, mid, lid, name, descr, label);
	}
	
	/**
     * Deletes a location on a map.
	 * If the map or the location doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @param  lid the location identifier 
	 * @return	   true if the operation was successful
	 */
	 //voir si la réponse est cohérente si l'user et/ou la map et/ou la location n'existent pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/location/{location-id}")
	public boolean deleteLocation(@PathParam("user-id") int uid,
								  @PathParam("map-id") int mid,
								  @PathParam("location-id") int lid) {
		return userDAO.deleteLocation(uid, mid, lid);
	}
}