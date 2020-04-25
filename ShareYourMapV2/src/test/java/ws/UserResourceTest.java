package ws;

import org.junit.Assert;
import org.junit.Test;

import jetty_server.ws.UserResource;

public class UserResourceTest {

	@Test
	public void test() {
		UserResource ur = new UserResource();
		
		//	---------------- 1st get ---------------- //
		Assert.assertEquals(2, ur.getUsers().size());
		Assert.assertEquals("David",ur.getUser(1).getName());
		
		//	---------------- Create elements ---------------- //
		Assert.assertNotNull(ur.createUser("user3", "password", "password"));
		Assert.assertEquals(3, ur.getUsers().size());
		Assert.assertEquals("user3",ur.getUser(3).getName());
		Assert.assertNotNull(ur.createUser("user4", "password", "password"));
		
		ur.createUser("user5", "pass", "password");
		ur.createUser("David", "password", "password");
		Assert.assertEquals(4, ur.getUsers().size());
		
		//	---------------- Edit elements ---------------- //
		ur.editUser(1, "ddd", "ts", "cpassword");
		ur.editUser(1, "aa", "ts", "ts");
		Assert.assertEquals("ddd",ur.getUser(1).getPassword());
		ur.editUser(1, "ddd", "dav", "dav");
		Assert.assertEquals("dav",ur.getUser(1).getPassword());

		//	---------------- Delete elements ---------------- //
		Assert.assertFalse(ur.deleteUser(8));
		Assert.assertTrue(ur.deleteUser(3));
		Assert.assertEquals(3, ur.getUsers().size());
	}

}
