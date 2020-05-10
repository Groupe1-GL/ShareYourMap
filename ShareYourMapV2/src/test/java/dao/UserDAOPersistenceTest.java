package dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory; 

import org.junit.Assert;
import org.junit.Test;

import classes.Map;
import classes.User;
import dao.datanucleus.UserDAOPersistence;

public class UserDAOPersistenceTest {

	@Test
	public static void test(PersistenceManagerFactory pmf) {
		//PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		UserDAOPersistence userDAO = new UserDAOPersistence(pmf);

		/* -------------------- Get elements (NULL) ------------------------------- */
		Assert.assertEquals(0, userDAO.getUsers().size());													
		
		
		/* -------------------- Create elements ------------------------------- */
		Assert.assertEquals("You've been successfully signed up.&viewmap/viewmap.html?uid=1",userDAO.createUser("user1","psw"));
		Assert.assertEquals(1, userDAO.getUsers().size());
		Assert.assertSame("user1",userDAO.getUser(1).getName());
		Assert.assertSame("user1",userDAO.getUser("user1").getName());
		Assert.assertNull(userDAO.getUser(8));
		
		Assert.assertEquals("This username is already used.",userDAO.createUser("user1","psw"));
		Assert.assertEquals("You've been successfully signed up.&viewmap/viewmap.html?uid=2",userDAO.createUser("user2","psw"));
		Assert.assertEquals("You've been successfully signed up.&viewmap/viewmap.html?uid=3",userDAO.createUser("user3","psw"));
		Assert.assertEquals(3, userDAO.getUsers().size());
		Assert.assertSame("user2",userDAO.getUser(2).getName());
		
		
		/* -------------------- Edit elements ------------------------------- */
		Assert.assertEquals("User not found!",userDAO.editUser(6,"psw"));
		Assert.assertEquals("Password successfully updated!",userDAO.editUser(2,"new_psw"));								
		Assert.assertSame("new_psw", userDAO.getUser(2).getPassword());	
		
		List<Map> maps = new ArrayList<Map>();
		maps.add(new Map("map1","user1",true));
		Assert.assertTrue(userDAO.editUsersMaps(1, maps));
		Assert.assertEquals(maps,userDAO.getUser(1).getMaps());
		
//		/* -------------------- Delete elements ------------------------------- */
		Assert.assertFalse(userDAO.deleteUser(0));
		Assert.assertEquals(3, userDAO.getUsers().size());
		Assert.assertTrue(userDAO.deleteUser(2));
		Assert.assertEquals(2, userDAO.getUsers().size());
	}

}
