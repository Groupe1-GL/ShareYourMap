import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import classes.Map;
import classes.MapContainer;


public class MapContainerTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		Long containerId = null;

		// Save a container with 3 Maps
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			Map Map1 = new Map("Map1", "me", true);
			Map Map2 = new Map("Map2", "me", true);
			Map Map3 = new Map("Map3", "me", true);

			MapContainer container = new MapContainer();
			container.getMaps().add(Map1);
			container.getMaps().add(Map2);
			container.getMaps().add(Map3);

			container = pm.makePersistent(container);
			containerId = container.getId();
			pm.close();
		}

		// Retrieve this container
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			MapContainer container = pm.getObjectById(MapContainer.class, containerId);
			Assert.assertEquals(3, container.getMaps().size());

			pm.close();
		}

		pmf.close();
	}

}
