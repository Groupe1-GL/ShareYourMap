package dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import dao.datanucleus.LocationDAOPersistence;

public class LocationDAOPersistenceTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		LocationDAOPersistence locationDAO = new LocationDAOPersistence(pmf);
		
		/* -------------------- Get elements (NULL) ------------------------------- */
		Assert.assertNull(locationDAO.getLocation(1));								
		
		/* -------------------- Create elements ------------------------------- */
		Assert.assertTrue(locationDAO.createLocationOnMap(1, 1, "Name_test1", "Descrip_test1", "Label_test1", 1, 2));
		Assert.assertSame("Name_test1",locationDAO.getLocation(1).getName());		
		
		/* -------------------- Edit elements ------------------------------- */
		
		/* -------------------- Delete elements ------------------------------- */
		Assert.assertFalse(locationDAO.deleteLocation(0, 0, 0));								
		Assert.assertTrue(locationDAO.deleteLocation(0, 0, 1)); 
	}

}
