package ws;

import org.junit.Assert;
import org.junit.Test;

import classes.User;
import jetty_server.ws.UserResource;

public class UserResourceTest {

	@Test
	public void test() {
		UserResource ur = new UserResource();
		
// UserDAOPersistence		
		//	---------------- 1st get ---------------- //
		Assert.assertEquals(0, ur.getUsers().size());
		
		
		//	---------------- Create elements ---------------- //
		User u1 = new User(0,"user1","psw1");
		Assert.assertEquals("You've been successfully signed up.&viewmap/viewmap.html?uid=1",ur.createUser(u1));
		Assert.assertEquals(1, ur.getUsers().size());
		Assert.assertEquals("user1",ur.getUser(1).getName());
		User u11 = new User(0,"user1","psw");
		Assert.assertEquals("This username is already used.",ur.createUser(u11));
		Assert.assertEquals(1, ur.getUsers().size());
		
		User u2 = new User(0,"user2","psw2");
		ur.createUser(u2);
		ur.createUser(new User("user3","psw3"));
		Assert.assertEquals(2, ur.getUser(2).getId());
		
		
		//		---------------- Connexion ---------------- //
		User u4 = new User(0,"user4","psw4");
		Assert.assertEquals("This user doesn't exist",ur.connectUser(u4));
		u1.setPassword("psw2");
		Assert.assertEquals("Username and password do not match",ur.connectUser(u1));
		u1.setPassword("psw1");
		Assert.assertEquals("Welcome back ^^ &/viewmap/viewmap.html?uid=1",ur.connectUser(u1));
		
		//	---------------- Edit elements ---------------- //
		u2.setPassword("new_psw2");
		Assert.assertEquals("User not found!",ur.editUser(6, u1));
		Assert.assertEquals("Password successfully updated!",ur.editUser(2, u2));
		Assert.assertEquals("new_psw2",ur.getUser(2).getPassword());					

		
		//	---------------- Delete elements ---------------- //
		Assert.assertFalse(ur.deleteUser(8));
		Assert.assertTrue(ur.deleteUser(2));
		Assert.assertEquals(2, ur.getUsers().size());
		
	}

}
