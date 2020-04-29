import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import classes.Location;
import classes.LocationContainer;

public class LocationContainerTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		Long containerId = null;

		// Save a container with 3 Locations
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			Location Location1 = new Location("Loc1", "me", 1, 1, null, null);
			Location Location2 = new Location("Loc2", "me", 2, 2, null, null);
			Location Location3 = new Location("Loc3", "me", 3, 3, null, null);

			LocationContainer container = new LocationContainer();
			container.getLocations().add(Location1);
			container.getLocations().add(Location2);
			container.getLocations().add(Location3);

			container = pm.makePersistent(container);
			containerId = container.getId();
			pm.close();
		}

		// Retrieve this container
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			LocationContainer container = pm.getObjectById(LocationContainer.class, containerId);
			Assert.assertEquals(3, container.getLocations().size());

			pm.close();
		}

		pmf.close();
	}

}
