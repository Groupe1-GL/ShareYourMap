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
		UserDAOPersistenceTest.test();

		
		/* -------------------- Init ------------------------------- */
		Assert.assertEquals(1, mapDAO.getMaps().size());							
		Assert.assertEquals("This username is already used.", mapDAO.getUserDAO().createUser("user1", "psw"));
		
		/* -------------------- Create elements ------------------------------- */
		Assert.assertTrue(mapDAO.createMap(1, "test2", false));
		Assert.assertEquals(2, mapDAO.getMaps().size());
		Assert.assertTrue(mapDAO.createMap(1, "test3", true));
		Assert.assertTrue(mapDAO.createMap(3, "test4", true));
		Assert.assertEquals("test4",mapDAO.getUserDAO().getUser(3).getMaps().get(0).getName());
		Assert.assertTrue(mapDAO.createMap(3, "test5", true));
		Assert.assertEquals("test5",mapDAO.getUserDAO().getUser(3).getMaps().get(1).getName());
		//Assert.assertEquals("testc",mapDAO.getMap(2).getName());
	
		Assert.assertFalse(mapDAO.createMap(2, "test5", true));

		Assert.assertTrue(mapDAO.createMap(1, "test6",true));
		Assert.assertTrue(mapDAO.createMap(1, "test7",true));


		/* -------------------- Edit elements ------------------------------- */
		Assert.assertTrue(mapDAO.editMap(1, 1, "new", true));
		for (int i = 0; i < 7; i++) {
			System.out.println("id "+mapDAO.getMaps().get(i).getID()+" "+mapDAO.getMaps().get(i).getName());
		}
		//System.out.println(mapDAO.getMap(11).getName());
		//Assert.assertEquals("new",mapDAO.getMap(2).getName());
		
		/* -------------------- Delete elements ------------------------------- */
		//Assert.assertFalse(mapDAO.deleteMap(0, 7));
		//Assert.assertEquals(3, mapDAO.getMaps().size());
		//Assert.assertTrue(mapDAO.deleteMap(0, 1));
		//Assert.assertEquals(2, mapDAO.getMaps().size());
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