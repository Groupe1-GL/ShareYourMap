package dao.datanucleus;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import classes.User;
import dao.UserDAO;

public class UserDAOPersistence implements UserDAO {

	private PersistenceManagerFactory pmf;
	
	public UserDAOPersistence(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	
	@SuppressWarnings({ "unchecked", "finally" })
	public List<User> getUsers() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		List<User> users = null;
		List<User> detached = new ArrayList<User>();
		try {
			tx.begin();
			
			Query q = pm.newQuery(User.class);
			users = (List<User>) q.execute("");
			detached = (List<User>) pm.detachCopyAll(users);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
			return detached;
		}
	}
	
	
	public User getUser(int uid) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		User us = null;
		User detached = null;
		try {
			tx.begin();

			Query q = pm.newQuery(User.class);
			q.declareParameters("Integer uid");
			q.setFilter("uid == id");
			q.setUnique(true);

			us = (User) q.execute(uid);
			detached = (User) pm.detachCopy(us);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return detached;
	}
	
	@SuppressWarnings("finally")
	public Response createUser(String name, String password, String cpassword) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		boolean alreadyExist = false;
		for(User us: this.getUsers()) {
			if(us.getName().equals(name)) {
				alreadyExist = true;
			}
		}
		
		try {
			tx.begin();
			if (alreadyExist){
				return Response
						.status(400)
						.entity("This username is already used.")
						.build();
			}
			//Check if the passwords are equals
			if (password.equals(cpassword)){
				User newUser = new User(name,password);
				pm.makePersistent(newUser);
				tx.commit();
			}
			else {
				return Response
						.status(400)
						.entity("The passwords do not match.")
						.build();
			}		
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				pm.close();
				return null;
			}
			pm.close();
			return Response
			.status(201)
			.entity("You've been successfully signed up.")
			.build();
		}
	}
	
	@SuppressWarnings("finally")
	public boolean editUser2(int uid, String opassword, String password, String cpassword) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		//User u = this.getUser(uid);
		boolean res = false;
		
		try {
			tx.begin();
			User u = pm.getObjectById(User.class,1);
			if (opassword != null && password != null && cpassword != null){
				if (opassword.equals(u.getPassword()) && password.equals(cpassword)){
					u.setPassword(password);
					tx.commit();
					res = true;
				}
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
			return res;		
		}
		
	}
	

	@SuppressWarnings("finally")
	public Response editUser(int uid, String opassword, String password, String cpassword) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		User u = this.getUser(uid);
		
		try {
			tx.begin();
			if (!opassword.equals(null)&&!password.equals(null)&&!cpassword.equals(null)){
				if (opassword.equals(u.getPassword())&&password.equals(cpassword)){
					Query q = pm.newQuery("UPDATE User SET this.psw="+password+" WHERE this.id="+uid);
					q.execute();
				}
				else {
					return Response
							.status(400)
							.entity("The passwords do not match.")
							.build();
				}			
			}
			return Response
						.status(404)
						.entity("User not found!")
						.build();	
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				return null;
			}
			pm.close();
			return Response
					.status(200)
					.entity("Password successfully updated!")
					.build();		
		}
		
	}

	@SuppressWarnings("finally")
	public boolean deleteUser(int uid) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = true;
		try {
			tx.begin();
			//User u = this.getUser(uid);
			//User t = pm.detachCopy(u);
			User u = pm.getObjectById(User.class,"1");
			pm.makePersistent(u);
			pm.deletePersistent(u);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				res = false;
			}
			pm.close();
			return res;		
		}
	}

	public Response connectUser(String username, String password) {
		User us = null;
		for(User u: this.getUsers()) {
			if(u.getName().equals(username)) {
				us = u;
			}
		}
		if (us != null && us.getPassword().equals(password)) {
			return Response.status(Response.Status.SEE_OTHER)
			         .header(HttpHeaders.LOCATION, "/viewmap/viewmap.html")
			         .header("X-Foo", "bar")
			         .build();
		}
		 return Response
				 	.status(402)
		            .entity("Username and password do not match")
		            .build();
	}


	public User getUser(String username) {
		return null;
	}

}
