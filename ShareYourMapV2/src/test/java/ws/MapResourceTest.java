package ws;

import org.junit.Assert;
import org.junit.Test;

import jetty_server.ws.MapResource;

public class MapResourceTest {

	@Test
	public void test() {
		MapResource mr = new MapResource();
		
		//	---------------- 1st get ---------------- //
		Assert.assertEquals(2, mr.getMaps().size());
		Assert.assertEquals("Shop",mr.getMap(3).getName());
		Assert.assertNull(mr.getMap(10));
		
		//	---------------- Create elements ---------------- //
		Assert.assertNotNull(mr.createMap(1,"Map3"));
		Assert.assertEquals(3, mr.getMaps().size());
		Assert.assertEquals("Map3",mr.getMap(1).getName());
		Assert.assertNotNull(mr.createMap(1,"Map4"));
		
		mr.createMap(3,"Map5");
		Assert.assertEquals(4, mr.getMaps().size());
	
		//	---------------- Edit elements ---------------- //
		mr.editMap(5,1, "ddd",1);
		mr.editMap(1,9, "ddd",1);
		Assert.assertEquals("Map3",mr.getMap(1).getName());
		Assert.assertNull(mr.getMap(9));
		
		mr.editMap(1,1, "New_map3",1);
		Assert.assertEquals("New_map3",mr.getMap(1).getName());

		//	---------------- Delete elements ---------------- //
		Assert.assertFalse(mr.deleteMap(1,8));
		Assert.assertTrue(mr.deleteMap(2,3));
		Assert.assertEquals(3, mr.getMaps().size());
	}

}
