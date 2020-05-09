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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)	
	public String createUser(User u) {
		String name = u.getName();
		String password = u.getPassword();
		return userDAO.createUser(name, password);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)	
	public String connectUser(User u) {
		String name = u.getName();
		String password = u.getPassword();
		User us = userDAO.getUser(name);
		if (us == null) {
			return "This user doesn't exist";
		}
		else if(!us.getPassword().equals(password)) {
			return "Username and password do not match";
		}
		 return "Welcome back ^^ &/viewmap/viewmap.html?uid="+us.getId();
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public User getUser(@PathParam("user-id") int uid) {
		return userDAO.getUser(uid);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{user-id}")
	public String editUser(@PathParam("user-id") int uid, User u) {
		String password = u.getPassword();
		return userDAO.editUser(uid, password);
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{user-id}")
	public boolean deleteUser(@PathParam("user-id") int uid) {
		return userDAO.deleteUser(uid);
	}

	
}