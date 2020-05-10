package dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import dao.datanucleus.UserDAOPersistence;

public class UserDAOPersistenceTest {

	@Test
	public static void test(PersistenceManagerFactory pmf) {
		//PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		UserDAOPersistence userDAO = new UserDAOPersistence(pmf);

		/* -------------------- Get elements (NULL) ------------------------------- */
		Assert.assertEquals(0, userDAO.getUsers().size());		
		Assert.assertEquals(null, userDAO.getUser(0));		
		
		/* -------------------- Create elements ------------------------------- */
		userDAO.createUser("David","ddd");
		Assert.assertEquals(1, userDAO.getUsers().size());
		System.out.println(userDAO.getUser(1).getId());
		
		Assert.assertNull(userDAO.getUser(8));//		
		userDAO.createUser("user2","psw");
		userDAO.createUser("user3","psw");
		Assert.assertEquals(3, userDAO.getUsers().size());
		
		/* -------------------- Edit elements ------------------------------- */
		Assert.assertEquals("User not found!",userDAO.editUser(4,"psw"));
		Assert.assertEquals("Password successfully updated!",userDAO.editUser(1,"psw")); 									
		Assert.assertSame("psw", userDAO.getUser(1).getPassword());																//Probleme de ré-indexion
		
		/* -------------------- Delete elements ------------------------------- */
		Assert.assertFalse(userDAO.deleteUser(0));
		Assert.assertEquals(3, userDAO.getUsers().size());
		Assert.assertTrue(userDAO.deleteUser(2));
		Assert.assertEquals(2, userDAO.getUsers().size());
	}

}
