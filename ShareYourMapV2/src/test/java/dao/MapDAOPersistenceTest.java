package dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import classes.Map;
import dao.datanucleus.MapDAOPersistence;

public class MapDAOPersistenceTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		MapDAOPersistence mapDAO = new MapDAOPersistence(pmf);

		
		/* -------------------- Init ------------------------------- */
		Assert.assertEquals(0, mapDAO.getMaps().size());							
		Assert.assertEquals("You've been successfully signed up.&viewmap/viewmap.html?uid=1", mapDAO.getUserDAO().createUser("user1", "psw"));
		System.out.println(mapDAO.getUserDAO().getUser(1).getMaps());
		
		/* -------------------- Create elements ------------------------------- */
		Assert.assertFalse(mapDAO.createMap(2, "test1", true));
		Assert.assertEquals(0, mapDAO.getMaps().size());
		Assert.assertTrue(mapDAO.createMap(1, "test1", true));
		Assert.assertTrue(mapDAO.createMap(1, "test2",true));
		Assert.assertEquals(2, mapDAO.getMaps().size());


		/* -------------------- Edit elements ------------------------------- */
		/*Assert.assertFalse(mapDAO.editMap(0, 5, "new", true));
		Assert.assertTrue(mapDAO.editMap(0, 2, "new_test2", true));		
		Assert.assertSame("new_test2", mapDAO.getMap(2).getName());*/
		
		
		/* -------------------- Delete elements ------------------------------- */
		/*Assert.assertFalse(mapDAO.deleteMap(0, 7));
		Assert.assertEquals(3, mapDAO.getMaps().size());
		Assert.assertTrue(mapDAO.deleteMap(0, 1));
		Assert.assertEquals(2, mapDAO.getMaps().size());*/
	}

}

/**
* Insert of object "classes.Map@4b79ac84"  "INSERT INTO "MAP" (ACCESS,CREATORNAME,ID,"NAME",SHAREDID,LISTMAP_USER_ID_OID,LISTMAP_INTEGER_IDX) VALUES (?,?,?,?,?,?,?)" failed : 
* Column count does not match in statement [INSERT INTO "MAP" (ACCESS,CREATORNAME,ID,"NAME",SHAREDID,LISTMAP_USER_ID_OID,LISTMAP_INTEGER_IDX) VALUES (?,?,?,?,?,?,?)]
*/