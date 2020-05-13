package dao.datanucleus;

import classes.Map;
import dao.UserDAO;
import classes.User;
import java.util.List;
import javax.jdo.Query;
import java.util.ArrayList;
import javax.jdo.Transaction;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * UserDAOPersistence is the implementation of the UserDAO interface with DataNucleus.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
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
			pm.setDetachAllOnCommit(true);
			
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				return detached;
			}
			pm.close();
		}
		return detached;
	}
	
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
			
			User u = this.getUser(uid);			
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

	public boolean editUsersMaps(int uid, Map map) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;		
		try {
			tx.begin();
			
			User u = this.getUser(uid);
			if (u != null && u.getMaps().add(map)) {
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
