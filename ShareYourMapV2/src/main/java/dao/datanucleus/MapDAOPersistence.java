package dao.datanucleus;
 
import java.util.ArrayList;
import java.util.List; 
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response; 
import classes.Location;
import classes.Map;
import classes.User;
import dao.MapDAO;
 
/**
 * UserDAOPersistence is the implementation of the UserDAO interface with DataNucleus.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
public class MapDAOPersistence implements MapDAO{
   
    private PersistenceManagerFactory pmf;
    private UserDAOPersistence userDAO;
    
    public MapDAOPersistence(PersistenceManagerFactory pmf) {
        this.pmf = pmf;
        this.userDAO = new UserDAOPersistence(pmf);
    }
 
    public UserDAOPersistence getUserDAO() {
    	return this.userDAO;
    }
    
    @SuppressWarnings("unchecked")
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
        Map detached = null;
        try {
        	tx.begin();
        	
            detached = pm.getObjectById(Map.class, mid);
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
   
 
    @SuppressWarnings("finally")
    public boolean createMap(int uid, String name, boolean access){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean res = false;
        try {
            tx.begin(); 
            
            User us = userDAO.getUser(uid);
            if (us != null) {
                userDAO.editUsersMaps(uid, new Map(name, us.getName(), access));
                res = true;
            }
            
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
                pm.close();
            }
            pm.close();
            return res;
        }
    }
 
    
    public boolean editMap(int uid, int mid, String name, boolean access){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean res = false;
        try {
            tx.begin();
            
            Map m = this.getMap(mid);
            if((m != null)&&m.setAccess(access)&&m.setName(name)) {
            	pm.makePersistent(m);
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
 
    public Response getSharedMap(int uid, int mid, String sharedId) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Response res = Response
                .status(424)
                .entity("Transaction method failed.")
                .build();
        try {
            tx.begin();
            Map m = this.getMap(mid);
            if (sharedId.equals(m.getSharedID())) {
                return Response
                         .status(Response.Status.SEE_OTHER)
                         .header(HttpHeaders.LOCATION, "/sharemap/sharemap.html?id=" + mid + "&shared-id=" + sharedId + "&uid=" + uid)
                         .header("X-Foo", "bar")
                         .build();
            }
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
                pm.close();
            }
        }
        return res;
    }
 
    @SuppressWarnings("finally")
    public boolean deleteMap(int uid, int mid){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean res = false;
        User us = userDAO.getUser(uid);
        try {
            tx.begin();
            
            Map m = this.getMap(mid);
            if(m != null) {
            	if ((userDAO.getUser(m.getCreatorName()).getId()==uid)) {
	                Query q = pm.newQuery(Map.class);
	                q.declareParameters("Integer mid");
	                q.setFilter("id == mid");
	                q.deletePersistentAll(mid);
            	}
            	else {
            		List<Map> newList = us.getMaps();
            		newList.remove(m);
            		userDAO.editUsersMaps(uid, newList);            		
            	}
                res = true;
            }
            
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
            return res;
        }
    }
   
    @SuppressWarnings("finally")
    public boolean editMapsLocation(int uid, int mid, List<Location> locations){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean res = false;
        try {
            tx.begin();
            
            Map m = this.getMap(mid);
            if((m != null)&&m.setLocation(locations)) {
            	pm.makePersistent(m);
                res = true;
            }
            
            tx.commit();   
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
            return res;
        }
    }    
    
    public boolean editMapsLocation(int uid, int mid, Location location){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean res = false;
        try {
            tx.begin();
            
            Map m = this.getMap(mid);
            if((m != null)&&m.getLocations().add(location)) {
            	pm.makePersistent(m);
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