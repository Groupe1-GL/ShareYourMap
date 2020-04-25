package ws;

import org.junit.Assert;
import org.junit.Test;

import jetty_server.ws.LocationResource;

public class LocationResourceTest {

	@Test
	public void test() {
		LocationResource lo = new LocationResource();
		
		//	---------------- Create / Delete elements ---------------- //
		Assert.assertTrue(lo.createLocationOnMap(1,4,"Location1","desc","lab",2.334,3.232));
		Assert.assertFalse(lo.createLocationOnMap(10,1,"Location2","desc","lab",2.334,3.232));
		Assert.assertTrue(lo.createLocationOnMap(1,3,"Location2.2","desc","lab",2.334,3.232));

		Assert.assertTrue(lo.deleteLocation(1,3,1));
		Assert.assertFalse(lo.deleteLocation(1,3,5));
	
		//	---------------- Edit elements ---------------- //
		Assert.assertFalse(lo.contributeOnLocation(1,3,0,"coucou"));
		Assert.assertFalse(lo.contributeOnLocation(1,43,2,"coucou"));
		Assert.assertFalse(lo.contributeOnLocation(10,3,0,"coucou"));
		Assert.assertTrue(lo.contributeOnLocation(1,3,2,"coucou"));
	}

}
