package ws;

import org.junit.Assert;
import org.junit.Test;

import jetty_server.ws.UserResource;

public class UserResourceTest {

	@Test
	public void test() {
		UserResource ur = new UserResource();
		
// UserDAOPersistence		
		//	---------------- 1st get ---------------- //
		Assert.assertEquals(0, ur.getUsers().size());
		
		//	---------------- Create elements ---------------- //
		/*Assert.assertNotNull(ur.createUser("user1", "password", "password"));
		Assert.assertEquals(1, ur.getUsers().size());
		Assert.assertEquals("user1",ur.getUser(1).getName());
		Assert.assertNotNull(ur.createUser("user2", "password", "password"));
		
		ur.createUser("user3", "pass", "password");
		ur.createUser("user1", "password", "password");
		Assert.assertEquals(2, ur.getUsers().size());
		*/
		//	---------------- Edit elements ---------------- //
		/*ur.editUser(1, "password", "ts", "cpassword");
		ur.editUser(1, "aa", "ts", "ts");
		Assert.assertEquals("password",ur.getUser(1).getPassword());
		ur.editUser(1, "password", "new_psw", "new_psw");
		Assert.assertEquals("new_psw",ur.getUser(3).getPassword());	*/						// Problème de ré-indexion

		//	---------------- Delete elements ---------------- //
		Assert.assertFalse(ur.deleteUser(8));
		Assert.assertTrue(ur.deleteUser(2));
		Assert.assertEquals(1, ur.getUsers().size());
		
	}

}
