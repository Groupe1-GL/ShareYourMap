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
 * @version %I%, %G%
 * @since 1.0
 */
@Path("/users")
public class UserResource {
	
	UserDAO userDAO = new UserDAOImpl();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
		return userDAO.getUsers();
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response createUser(
		@FormParam("username") String name,
		@FormParam("passwd") String password,
		@FormParam("cpasswd") String cpassword,
		@FormParam("email") String email) {
		return userDAO.createUser(name, password, cpassword, email);
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public User getUser(@PathParam("user-id") int uid) {
		return userDAO.getUser(uid);
	}
	
	
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
	
	
	 //voir si la réponse est cohérente si l'user n'existe pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public boolean deleteUser(@PathParam("user-id") int uid) {
		return userDAO.deleteUser(uid);
	}
	
	
	 //voir si la réponse est cohérente si l'user n'existe pas
	//voir si inutile, mais permet d'encapsuler et protéger credentials
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps")
	public List<Map> getMapsOfUser(@PathParam("user-id") int uid) {
		return userDAO.getMapsOfUser(uid);
	}
	
	
	 //voir si la réponse est cohérente si l'user et/ou la map n'existent pas
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/{map-id}")
	public boolean addMapOnUser(@PathParam("user-id") int uid,
								@PathParam("map-id") int mid) {
		return userDAO.addMapOnUser(uid, mid);
	}
	
	
	 //voir si la réponse est cohérente si l'user et/ou la map n'existent pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}/maps/{map-id}")
	public boolean removeMapOnUser(@PathParam("user-id") int uid,
								   @PathParam("map-id") int mid) {
		return userDAO.removeMapOnUser(uid, mid);
	}
	
}