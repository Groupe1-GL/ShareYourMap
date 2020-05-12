package dao.datanucleus;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import classes.Map;
import classes.User;
import dao.UserDAO;

public class UserDAOPersistence implements UserDAO {

	private PersistenceManagerFactory pmf;
	
	public UserDAOPersistence(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
	
	
	@SuppressWarnings("unchecked")
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
		User detached = null;
		try {
			tx.begin();
			detached = pm.getObjectById(User.class, uid);
			//List<Map> d2 = detached.getMaps(); useless since the persistence annotation on fetch group
			pm.setDetachAllOnCommit(true);
			tx.commit();
			
			/*
			 * ou version sans transaction:
			 * us = pm.getObjectById(User.class, uid);
			 * detached = pm.detachCopy(us);
			 */
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				return detached;
			}
			pm.close();
		}
		return detached;
	}
	
	//non présent sur les webservices
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
	
	
	public String createUser(String name, String password) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		String res = "Transaction method failed.";
		
		boolean alreadyExist = false;
		if( name != null && getUser(name) != null) {
			alreadyExist = true;
		}
		
		try {
			tx.begin();
			if (alreadyExist){
				res = "This username is already used.";
			}
			else {
				User newUser = new User(name,password);
				pm.makePersistent(newUser);
				res = "viewmap/viewmap.html?uid="+newUser.getId();
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

	
	public String editUser(int uid, String password) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		String res = "Transaction method failed.";
						
		try {
			tx.begin();
			/*if (u != null){
				Query q = pm.newQuery(User.class);
				q.declareParameters("Integer uid");
				q.setFilter("uid == id");
				q.deletePersistentAll(uid);
				u.setPassword(password);
				pm.makePersistent(u);
				res = "Password successfully updated!";	
			}*/
			User us = this.getUser(uid);
			if (us != null) {
				us.setPassword(password);
				pm.makePersistent(us);
				res = "Password successfully updated!";
			}
			else {
				res = "User not found!";		
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
	
	public boolean deleteUser(int uid) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			User u = this.getUser(uid);
			if(u != null) {
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
	
	
	public boolean editUsersMaps(int uid, List<Map> maps) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		
		try {
			tx.begin();
			User u = this.getUser(uid); //detached User
			
			if (u != null && u.setMaps(maps)) {
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

	public boolean editUsersMaps(int uid, Map maps) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		
		try {
			tx.begin();
			User u = this.getUser(uid); //detached User
			
			if (u != null && u.getMaps().add(maps)) {
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
