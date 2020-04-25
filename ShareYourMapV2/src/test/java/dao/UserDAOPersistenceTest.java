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
		
		/* -------------------- Create elements ------------------------------- */
		Assert.assertNotNull(userDAO.createUser("user1","psw","psw"));
		Assert.assertEquals(1, userDAO.getUsers().size());
		
		Assert.assertSame("user1",userDAO.getUser(1).getName());
		
		Assert.assertNotNull(userDAO.createUser("user2","psw","psw"));
		Assert.assertNotNull(userDAO.createUser("user3","psw","psw"));
		Assert.assertEquals(3, userDAO.getUsers().size());
		Assert.assertNull(userDAO.getUser(8));
		
		Assert.assertSame("user2",userDAO.getUser(2).getName());
		
		/* -------------------- Edit elements ------------------------------- */
		/*Assert.assertFalse(userDAO.editUser2(9,"","new",""));
		Assert.assertTrue(userDAO.editUser2(1,"psw","new_psw","new_psw")); 									
		Assert.assertSame("new_psw", userDAO.getUser(1).getPassword());*/
		
		/* -------------------- Delete elements ------------------------------- */
		Assert.assertFalse(userDAO.deleteUser(0));
		Assert.assertTrue(userDAO.deleteUser(1));
		Assert.assertEquals(2, userDAO.getUsers().size());
	}

}
