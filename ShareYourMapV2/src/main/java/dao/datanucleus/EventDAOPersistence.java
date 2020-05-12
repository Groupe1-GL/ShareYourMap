package dao.datanucleus;

import java.time.LocalDateTime;

import javax.jdo.PersistenceManagerFactory;

import dao.EventDAO;

public class EventDAOPersistence extends LocationDAOPersistence implements EventDAO{

	public EventDAOPersistence(PersistenceManagerFactory pmf) {
		super(pmf);
		// TODO Auto-generated constructor stub
	}

	public boolean createEventOnMap(int uid, int mid, String name, String descr, String label, double x, double y,
			LocalDateTime start, LocalDateTime end) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteEvent(int uid, int mid, int eid) {
		// TODO Auto-generated method stub
		return false;
	}

}
