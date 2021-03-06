package dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import classes.Location;
import classes.Map;
import classes.User;
import dao.datanucleus.LocationDAOPersistence;

public class LocationDAOPersistenceTest {

	@Test
	public  void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		LocationDAOPersistence locationDAO = new LocationDAOPersistence(pmf);
		MapDAOPersistenceTest.test();
		
		/* -------------------- Get elements (NULL) ------------------------------- */
		Assert.assertNull(locationDAO.getLocation(1));								
		
		/* -------------------- Create elements ------------------------------- */
		Assert.assertTrue(locationDAO.createLocationOnMap(1, 1, "Name_test1", "Descrip_test1", "Label_test1", 1, 2));
		Assert.assertTrue(locationDAO.createLocationOnMap(0, 1, "Name_test2", "Descrip_test2", "Label_test2", 5.11, 2));
		Assert.assertTrue(locationDAO.createLocationOnMap(1, 3, "Name_test3", "Descrip_test3", "Label_test2", 2.11, 21.02222222));
		Assert.assertTrue(locationDAO.createLocationOnMap(1, 3, "Name_test4", "Descrip_test4", "Label_test4", 1, 2));
		
		/* -------------------- Contribute on Locations ------------------------------- */
		Assert.assertTrue(locationDAO.contributeOnLocation(1, 1, 0, "test3"));
		Assert.assertTrue(locationDAO.contributeOnLocation(1, 1, 0, "test4"));	
		
		/* -------------------- Edit elements ------------------------------- */
		
		Assert.assertTrue(locationDAO.editLocation(1, 1, 1, "renamed", "ok", "ok"));
		
		/* -------------------- Delete elements ------------------------------- */
		Assert.assertTrue(locationDAO.deleteLocation(1, 1, 3));		
		Assert.assertEquals("renamed", locationDAO.getLocation(1).getName());
		for (User u: locationDAO.getMapDAO().getUserDAO().getUsers()) {
			for (Map m: u.getMaps()) {
				System.out.println(u.getName()+":"+m.getID()+m.getName());
				for (Location l: m.getLocations()) {
					System.out.println(l.getID()+" "+l.getName());
					for (String message: l.getMessages()) {
						System.out.println(message);
					}
				}
			}
		}
	}
}
