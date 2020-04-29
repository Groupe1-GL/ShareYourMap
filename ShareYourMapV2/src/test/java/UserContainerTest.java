import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import classes.User;
import classes.UserContainer;

public class UserContainerTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		Long containerId = null;

		// Save a container with 3 Users
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			User User1 = new User("User1", "psw1");
			User User2 = new User("User2", "psw2");
			User User3 = new User("User3", "psw3");

			UserContainer container = new UserContainer();
			container.getUsers().add(User1);
			container.getUsers().add(User2);
			container.getUsers().add(User3);

			container = pm.makePersistent(container);
			containerId = container.getId();
			pm.close();
		}

		// Retrieve this container
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			UserContainer container = pm.getObjectById(UserContainer.class, containerId);
			Assert.assertEquals(3, container.getUsers().size());

			pm.close();
		}

		pmf.close();
	}

}
