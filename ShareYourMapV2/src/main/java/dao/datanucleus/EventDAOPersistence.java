package dao.datanucleus;

import java.time.LocalDateTime;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import classes.Event;
import classes.Location;
import classes.User;
import dao.EventDAO;

/**
 * EventDAOPersistence is the implementation of the EventDAO interface with DataNucleus.
 *
 * @author Mohamed Ahmed
 * @version 2.0
 * @since 1.0
 */
public class EventDAOPersistence extends LocationDAOPersistence implements EventDAO{

	public EventDAOPersistence(PersistenceManagerFactory pmf) {
		super(pmf);
	}

	public boolean createEventOnMap(int uid, int mid, String name, String descr, String label, double x, double y,
			LocalDateTime start, LocalDateTime end) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			
			User u = mapDAO.getUserDAO().getUser(uid);
			Location ev = new Event(name,u.getName(),x,y,descr,label,start,end);
			if (start.isBefore(end)) {
				if (mapDAO.editMapsLocation(uid,mid,ev)) {
					pm.makePersistent(ev);
					res = true;
				}			
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

	public boolean editEvent(int uid, int mid, int lid, String name, String descr, String label, LocalDateTime start,
			LocalDateTime end) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean res = false;
		try {
			tx.begin();
			Event l = (Event) getLocation(lid);
			if (l.setName(name)&&l.setDescription(descr)&&l.setLabel(label)&&l.setStart(start)&&l.setEnd(end)) {
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
}
