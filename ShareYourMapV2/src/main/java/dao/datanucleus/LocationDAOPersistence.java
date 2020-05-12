package dao.datanucleus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import classes.Location;
import classes.Map;
import classes.User;
import dao.LocationDAO;

public class LocationDAOPersistence implements LocationDAO{
	
	protected PersistenceManagerFactory pmf;
	protected MapDAOPersistence mapDAO;
	private UserDAOPersistence userDAO;
	
	public LocationDAOPersistence(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
		this.mapDAO = new MapDAOPersistence(pmf);
		
	}
	
	 public MapDAOPersistence getMapDAO() {
	    	return this.mapDAO;
	 }
	    
	
	@SuppressWarnings("finally")
    public Location getLocation(int lid){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Location detached = null;
        try {
        	tx.begin();
            detached = pm.getObjectById(Location.class, lid);
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
   


	public boolean createLocationOnMap(int uid, int mid, String name, String descr, String label, double x, double y) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			User u = mapDAO.getUserDAO().getUser(uid);
			Location loc = new Location(name,u.getName(),x,y,descr,label);
			Map m = mapDAO.getMap(mid);
			if (mapDAO.editMapsLocation(uid,mid,loc)) {
				pm.makePersistent(loc);
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

	public boolean contributeOnLocation(int uid, int mid, int lid, String message) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			Location l = getLocation(lid);
			if (l.addMessage(message)){
					pm.makePersistent(l);
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

	public boolean editLocation(int uid, int mid, int lid, String name, String descr, String label) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			Location l = getLocation(lid);
			if (l.setName(name)&&l.setDescription(descr)&&l.setLabel(label)) {
				pm.makePersistent(l);
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


	public boolean deleteLocation(int uid, int mid, int lid) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			Location l = this.getLocation(lid);																							// Need delay between the 2 queries
			if(l != null) {
				pm.deletePersistentAll(l);
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


	public boolean contributeOnLocationImg(int uid, int mid, int lid, InputStream uploadedInputStream,
			FormDataContentDisposition fileDetail) {
		String fileLocation = System.getProperty("user.dir") + "upload/img" + fileDetail.getFileName(); 
		try {  
            FileOutputStream out = new FileOutputStream(new File(fileLocation));  
            int read = 0;  
            byte[] bytes = new byte[1024];  
            out = new FileOutputStream(new File(fileLocation));  
            while ((read = uploadedInputStream.read(bytes)) != -1) {  
                out.write(bytes, 0, read);  
            }  
            out.flush();  
            out.close();  
        } catch (IOException e) {e.printStackTrace();} 
		
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			Location l = getLocation(lid);
			l.addPicture(fileLocation);
			pm.deletePersistent(l);
			pm.makePersistent(l);
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
