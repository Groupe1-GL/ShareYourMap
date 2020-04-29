package jetty_server.ws;

import javax.jdo.JDOHelper;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import classes.User;
import dao.UserDAO;
import dao.UserDAOImpl;
import dao.datanucleus.UserDAOPersistence;

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
	
	//UserDAO userDAO = new UserDAOPersistence(JDOHelper.getPersistenceManagerFactory("gl"));
	UserDAO userDAO = new UserDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
		return userDAO.getUsers();
	}
	

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)	
	public Response createUser(
		@FormParam("username") String name,
		@FormParam("passwd") String password,
		@FormParam("cpasswd") String cpassword) {
		return userDAO.createUser(name, password, cpassword);
	}
	
	/*  This service tunnels the PUT request because the HTML Standard don't support PUT requests on
	 *	forms.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)	
	public Response connectUser(
		@FormParam("username") String name,
		@FormParam("passwd") String password,
		@FormParam("cpasswd") String cpassword,
		@FormParam("_METHOD") String method) {
		if (method.equals("PUT")&&!cpassword.equals(null)) {
			return createUser(name, password, cpassword);
		}
		else {
			User us = userDAO.getUser(name);
			if (us != null && us.getPassword().equals(password)) {
				return Response.status(Response.Status.SEE_OTHER)
				         .header(HttpHeaders.LOCATION, "/viewmap/viewmap.html?uid="+us.getId())
				         .header("X-Foo", "bar")
				         .build();
			}
			 return Response
					 	.status(401)
			            .entity("Username and password do not match")
			            .build();
		}
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
		@FormParam("cpasswd") String cpassword) {//voir si on modifie current_position
		return userDAO.editUser(uid, opassword, password, cpassword);
	}
	
	
	 //voir si la réponse est cohérente si l'user n'existe pas
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public boolean deleteUser(@PathParam("user-id") int uid) {
		return userDAO.deleteUser(uid);
	}
	


	
}