package dao.datanucleus;

import java.io.InputStream;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import classes.Location;
import dao.LocationDAO;

public class LocationDAOPersistence implements LocationDAO{
	
	private PersistenceManagerFactory pmf;
	private UserDAOPersistence userDAO;
	
	public LocationDAOPersistence(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
		this.userDAO = new UserDAOPersistence(pmf);
	}
	
	@SuppressWarnings("finally")
	public Location getLocation(int lid){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Location location = null;
		Location detached = null;
		try {
			tx.begin();

			Query q = pm.newQuery(Location.class);
			q.declareParameters("Integer lid");
			q.setFilter("lid == id");
			q.setUnique(true);
			
			location = (Location) q.execute(lid);
			detached = (Location) pm.detachCopy(location);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
			return detached;
		}
	}

	@SuppressWarnings("finally")
	public boolean createLocationOnMap(int uid, int mid, String name, String descr, String label, double x, double y) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			Location newFav = new Location(name,"",x,y,descr,label);
			pm.makePersistent(newFav);
			tx.commit();
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

	public boolean contributeOnLocation(int uid, int mid, int lid, String message) {
		return false;
	}

	public boolean editLocation(int uid, int mid, int lid, String message, String descr, String label) {
		return false;
	}

	@SuppressWarnings("finally")
	public boolean deleteLocation(int uid, int mid, int lid) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = true;
		try {
			tx.begin();
			Location l = this.getLocation(lid);																							// Need delay between the 2 queries
			if(l != null) {
				Query q = pm.newQuery(Location.class);
				q.declareParameters("Integer lid");
				q.setFilter("id == lid");
				q.deletePersistentAll(lid);
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


	public boolean contributeOnLocationImg(int uid, int mid, int lid, InputStream uploadedInputStream,
			FormDataContentDisposition fileDetail) {
		// TODO Auto-generated method stub
		return false;
	}

}
