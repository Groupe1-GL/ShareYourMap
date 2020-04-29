import java.time.LocalDateTime;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import classes.Event;
import classes.EventContainer;

public class EventContainerTest {

	@Test
	public void test() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		Long containerId = null;

		// Save a container with 3 Events
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			LocalDateTime s = LocalDateTime.now();
			LocalDateTime e = LocalDateTime.now().plusDays(2);
			
			Event Event1 = new Event("Event1", "me", 1, 1, null, null,s,e);
			Event Event2 = new Event("Event2", "me", 2, 2, null, null,s,e);
			Event Event3 = new Event("Event3", "me", 3, 3, null, null,s,e);

			EventContainer container = new EventContainer();
			container.getEvents().add(Event1);
			container.getEvents().add(Event2);
			container.getEvents().add(Event3);

			container = pm.makePersistent(container);
			containerId = container.getId();
			pm.close();
		}

		// Retrieve this container
		{
			PersistenceManager pm = pmf.getPersistenceManager();

			EventContainer container = pm.getObjectById(EventContainer.class, containerId);
			Assert.assertEquals(3, container.getEvents().size());

			pm.close();
		}

		pmf.close();
	}

}
