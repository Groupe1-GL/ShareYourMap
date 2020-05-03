package dao.datanucleus;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import classes.Location;
import classes.Map;
import classes.User;
import dao.MapDAO;

public class MapDAOPersistence implements MapDAO{
	
	private PersistenceManagerFactory pmf;
	private UserDAOPersistence userDAO;
	
	public MapDAOPersistence(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
		this.userDAO = new UserDAOPersistence(pmf);
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<Map> getMaps(){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		List<Map> maps = null;
		List<Map> detached = new ArrayList<Map>();
		try {
			tx.begin();

			Query q = pm.newQuery(Map.class);			
			maps = (List<Map>) q.execute("");
			detached = (List<Map>) pm.detachCopyAll(maps);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return detached;
	}
	
	public Map getMap(int mid){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Map map = null;
		Map detached = null;
		try {
			tx.begin();

			Query q = pm.newQuery(Map.class);
			q.declareParameters("Integer id");
			q.setFilter("mid == id");
			q.setUnique(true);
			
			map = (Map) q.execute(mid);
			detached = (Map) pm.detachCopy(map);

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
	public boolean createMap(int uid, String name){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			User us = userDAO.getUser(uid);			
			if (us != null) {
				Map newMap = new Map(name, us.getName());
				pm.makePersistent(newMap);
				
				List<Map> maps = us.getMaps();
				maps.add(newMap);
				userDAO.editUsersMaps(uid, maps);
				tx.commit();
				res = true;
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				pm.close();
				return false;
			}
			pm.close();
			return true;
		}
	}
	
	@SuppressWarnings("finally")
	public boolean createMap(int uid, int mid, String name, boolean access){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			User us = userDAO.getUser(uid);	
			if (us != null) {
				Map newMap = new Map(mid, name, us.getName(), true);
				pm.makePersistent(newMap);
				
				List<Map> maps = us.getMaps();
				maps.add(newMap);
				userDAO.editUsersMaps(uid, maps);
				tx.commit();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				pm.close();
				return false;
			}
			pm.close();
			return true;
		}
	}

	@SuppressWarnings("finally")
	public boolean editMap(int uid, int mid, String name, int access){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Map m = this.getMap(mid);
			User us = userDAO.getUser(uid);	
			if(m != null) {
				Query q = pm.newQuery(Map.class);
				q.declareParameters("Integer mid");
				q.setFilter("id == mid");
				q.deletePersistentAll(mid);
				us.getMaps().remove(m);
				
				createMap(uid, mid, name, access==1);
				tx.commit();	
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				pm.close();
				return false;
			}
			pm.close();
			return true;
		}
	}

	@SuppressWarnings("finally")
	public boolean deleteMap(int uid, int mid){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = true;
		User us = userDAO.getUser(uid);	
		try {
			tx.begin();
			Map m = this.getMap(mid);
			if(m != null) {
				Query q = pm.newQuery(Map.class);
				q.declareParameters("Integer mid");
				q.setFilter("id == mid");
				q.deletePersistentAll(mid);
				List<Map> maps = us.getMaps();
				maps.remove(m);
				userDAO.editUsersMaps(uid, maps);
				tx.commit();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				res = false;
			}
			pm.close();
			return res;
		}
	}
	
	@SuppressWarnings("finally")
	public boolean editMapsLocation(int uid, int mid, List<Location> locations){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Map m = this.getMap(mid);
			User us = userDAO.getUser(uid);	
			if(m != null) {
				Query q = pm.newQuery(Map.class);
				q.declareParameters("Integer mid");
				q.setFilter("id == mid");
				q.deletePersistentAll(mid);
				us.getMaps().remove(m);
				tx.commit();	
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				pm.close();
				return false;
			}
			pm.close();
			return true;
		}
	}

	public Response getSharedMap(int mid, String sharedID) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
	
	
