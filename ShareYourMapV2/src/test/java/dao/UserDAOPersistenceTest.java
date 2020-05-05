package dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import dao.datanucleus.UserDAOPersistence;

public class UserDAOPersistenceTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		UserDAOPersistence userDAO = new UserDAOPersistence(pmf);

		/* -------------------- Get elements (NULL) ------------------------------- */
		Assert.assertEquals(0, userDAO.getUsers().size());													
		
//		/* -------------------- Create elements ------------------------------- */
		Assert.assertEquals(200,userDAO.createUser("user1","psw","psw").getStatus());
//		Assert.assertEquals(1, userDAO.getUsers().size());
//		
//		Assert.assertSame("user1",userDAO.getUser(1).getName());
//		Assert.assertSame("user1",userDAO.getUser("user1").getName());
//		Assert.assertNull(userDAO.getUser(8));
//		
//		Assert.assertEquals("The passwords do not match.",userDAO.createUser("user2","psw","").getEntity());
//		Assert.assertEquals("This username is already used.",userDAO.createUser("user1","psw","psw").getEntity());
//		Assert.assertEquals(1, userDAO.getUsers().size());
//		
//		Assert.assertEquals("You've been successfully signed up.",userDAO.createUser("user2","psw","psw").getEntity());
//		Assert.assertEquals("You've been successfully signed up.",userDAO.createUser("user3","psw","psw").getEntity());
//		Assert.assertEquals(3, userDAO.getUsers().size());
//		Assert.assertSame("user2",userDAO.getUser(2).getName());
//		
//		/* -------------------- Edit elements ------------------------------- */
//		Assert.assertEquals("User not found!",userDAO.editUser(6,"psw","new","").getEntity());
//		Assert.assertEquals("The passwords do not match.",userDAO.editUser(1,"psw","new","").getEntity());
//		Assert.assertEquals("Password successfully updated!",userDAO.editUser(1,"psw","new_psw","new_psw").getEntity()); 									
//		Assert.assertSame("new_psw", userDAO.getUser(1).getPassword());																//Probleme de ré-indexion
//		
//		/* -------------------- Delete elements ------------------------------- */
//		Assert.assertFalse(userDAO.deleteUser(0));
//		Assert.assertEquals(3, userDAO.getUsers().size());
//		Assert.assertTrue(userDAO.deleteUser(2));
//		Assert.assertEquals(2, userDAO.getUsers().size());
	}

}
