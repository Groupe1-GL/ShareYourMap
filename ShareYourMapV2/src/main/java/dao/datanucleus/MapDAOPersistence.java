package dao.datanucleus;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import classes.Map;
import classes.User;
import dao.MapDAO;

public class MapDAOPersistence implements MapDAO{
	
	private PersistenceManagerFactory pmf;
	
	
	public MapDAOPersistence(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
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
			return detached;
		}
	}
	
	public Map getMap(int map_id){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Map map = null;
		Map detached = null;
		try {
			tx.begin();

			Query q = pm.newQuery(Map.class);
			q.declareParameters("Integer map_id");
			q.setFilter("map_id == id");
			q.setUnique(true);
			
			map = (Map) q.execute(map_id);
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
		try {
			tx.begin();
			User us = new User("name","psw");
			//User us = getUser(uid);								// Appel de UserDAO ?
			if (us != null) {
				Map newMap = new Map(uid, name, us.getName(), true);
				pm.makePersistent(newMap);
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
			if(m != null) {
				Query q = pm.newQuery("UPDATE Map SET this.name="+name+" WHERE this.id="+mid);
				q.execute();
				q = pm.newQuery("UPDATE Map SET this.access="+access+" WHERE this.id="+mid);
				q.execute();
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
		try {
			tx.begin();
			Map m = this.getMap(mid);
			if(m != null) {
				pm.makePersistent(m);
				pm.deletePersistent(m);
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
	
}
	
	
