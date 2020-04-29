package dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import dao.datanucleus.MapDAOPersistence;

public class MapDAOPersistenceTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		MapDAOPersistence mapDAO = new MapDAOPersistence(pmf);

		/* -------------------- Get elements (NULL) ------------------------------- */
		Assert.assertEquals(0, mapDAO.getMaps().size());							
		
		/* -------------------- Create elements ------------------------------- */
		Assert.assertTrue(mapDAO.createMap(1, "test1"));
		Assert.assertEquals(1, mapDAO.getMaps().size());
		Assert.assertTrue(mapDAO.createMap(2, "test2"));
		Assert.assertTrue(mapDAO.createMap(3, "test3"));
		Assert.assertEquals(3, mapDAO.getMaps().size());
		Assert.assertNull(mapDAO.getMap(5));
		
		/* -------------------- Edit elements ------------------------------- */
		Assert.assertFalse(mapDAO.editMap(0, 5, "new",1));
		Assert.assertTrue(mapDAO.editMap(0, 2, "new_test2",1));		
		Assert.assertSame("new_test2", mapDAO.getMap(2).getName());
		
		/* -------------------- Delete elements ------------------------------- */
		Assert.assertFalse(mapDAO.deleteMap(0, 7));
		Assert.assertEquals(3, mapDAO.getMaps().size());
		Assert.assertTrue(mapDAO.deleteMap(0, 1));
		Assert.assertEquals(2, mapDAO.getMaps().size());
	}

}
