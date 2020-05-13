package dao;


import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import org.junit.Assert;
import org.junit.Test;
import dao.datanucleus.MapDAOPersistence;

public class MapDAOPersistenceTest {

	@Test
	public static void test() {
		
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
		Assert.assertTrue(mapDAO.createMap(1, "test4", true));
		Assert.assertTrue(mapDAO.createMap(1, "test5", true));
		Assert.assertTrue(mapDAO.editMap(1, 1, "new", true));
		Assert.assertTrue(mapDAO.editMap(1, 1, "new2", true));
		Assert.assertTrue(mapDAO.createMap(1, "test6",true));
		Assert.assertTrue(mapDAO.createMap(1, "test7",true));


		/* -------------------- Edit elements ------------------------------- */
		Assert.assertTrue(mapDAO.editMap(1, 1, "new", true));
		Assert.assertTrue(mapDAO.editMap(1, 4, "new3", false));
		Assert.assertFalse(mapDAO.getMap(4).getAccess());
		Assert.assertEquals("test3",mapDAO.getMap(2).getName());
		
		/* -------------------- Delete elements ------------------------------- */
		Assert.assertFalse(mapDAO.deleteMap(0, 7));
		Assert.assertEquals(7, mapDAO.getMaps().size());
		/* -------------------- generateSharedID ------------------------------- */
		//testé sur le site, result: OK
		
	}

}

/**
* Insert of object "classes.Map@4b79ac84"  "INSERT INTO "MAP" (ACCESS,CREATORNAME,ID,"NAME",SHAREDID,LISTMAP_USER_ID_OID,LISTMAP_INTEGER_IDX) VALUES (?,?,?,?,?,?,?)" failed : 
* Column count does not match in statement [INSERT INTO "MAP" (ACCESS,CREATORNAME,ID,"NAME",SHAREDID,LISTMAP_USER_ID_OID,LISTMAP_INTEGER_IDX) VALUES (?,?,?,?,?,?,?)]
*/