package dao.datanucleus;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import classes.Map;
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
		}
		return detached;
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
	
	//non pr�sent sur les webservices
	public User getUser(String username) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		User us = null;
		User detached = null;
		try {
			tx.begin();

			Query q = pm.newQuery(User.class);
			q.declareParameters("String username");
			q.setFilter("username == name");
			q.setUnique(true);

			us = (User) q.execute(username);
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
	
	
	public Response createUser(String name, String password, String cpassword) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Response res = Response
				.status(424)
				.entity("Transaction method failed.")
				.build();	
		
		boolean alreadyExist = false;
		if(!name.equals(null)&&!getUser(name).equals(null)) {
			alreadyExist = true;
		}
		
		try {
			tx.begin();
			if (alreadyExist){
				res = Response
						.status(422)//voir quel est le meilleur code erreur
						.entity("This username is already used.")
						.build();
			}
			else {
				//Check if the passwords are equals
				if (password.equals(cpassword)){
					User newUser = new User(name,password);
					pm.makePersistent(newUser);
					res = Response
							.status(201)
							.entity("You've been successfully signed up.")
							.build();
				}
				else {
					res = Response
							.status(422)
							.entity("The passwords do not match.")
							.build();
				}
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				pm.close();
				return res;
			}
			pm.close();
		}
		return res; 
	}

	
	@SuppressWarnings("finally")
	public Response editUser(int uid, String opassword, String password, String cpassword) {
		//g�rer l'autorisation avec l'authentification
		//action possible uniquement si l'uid est �gal au uid de l'auth
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		User u = this.getUser(uid);
		Response res = Response
								.status(424)
								.entity("Transaction method failed.")
								.build();	
						
		try {
			tx.begin();
			if (!u.equals(null) && !opassword.equals(null) && !password.equals(null) && !cpassword.equals(null)){
				if (opassword.equals(u.getPassword()) && password.equals(cpassword)){
					Query q = pm.newQuery(User.class);
					q.declareParameters("Integer uid");
					q.setFilter("id == uid");
					q.deletePersistentAll(uid);
					u.setPassword(password);
					pm.makePersistent(u);
					res = Response
							.status(200)
							.entity("Password successfully updated!")
							.build();	
				}
				else {
					res = Response
							.status(422)
							.entity("The passwords do not match.")
							.build();
				}			
			}
			else {
				res = Response
							.status(404)
							.entity("User not found!")
							.build();	
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return res; 
	}
	

	@SuppressWarnings("finally")
	public boolean deleteUser(int uid) {
		//g�rer l'autorisation avec l'authentification
		//action possible uniquement si l'uid est �gal au uid de l'auth
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			User u = this.getUser(uid);
			if(!u.equals(null)) {
				Query q = pm.newQuery(User.class);
				q.declareParameters("Integer uid");
				q.setFilter("id == uid");
				q.deletePersistentAll(uid);
				res = true;
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();	
		}
		return res;	
	}
	
	
	@SuppressWarnings("finally")
	public boolean editUsersMaps(int uid, List<Map> maps) {
		//g�rer l'autorisation avec l'authentification
		//action possible uniquement si l'uid est �gal au uid de l'auth
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		
		try {
			tx.begin();
			User u = this.getUser(uid);
			if (!u.equals(null)){
				Query q = pm.newQuery(User.class);
				q.declareParameters("Integer uid");
				q.setFilter("id == uid");
				q.deletePersistentAll(uid);//supprimer l'elt pour �viter le doublon
				u.setMaps(maps);
				pm.makePersistent(u);
				res = true;
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();	
		}
		return res; 
	}

}
