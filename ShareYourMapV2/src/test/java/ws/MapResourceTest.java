package ws;

import org.junit.Assert;
import org.junit.Test;

import jetty_server.ws.MapResource;

public class MapResourceTest {

	@Test
	public void test() {
		MapResource mr = new MapResource();
		
		//		---------------- Create elements ---------------- //
		Assert.assertEquals(0, mr.getMaps().size());
		
		
		//		---------------- Create elements ---------------- //
	/*	Assert.assertFalse(mr.createMap(5,"map1"));
		Assert.assertTrue(mr.createMap(1,"map1"));
		Assert.assertEquals(1, mr.getMaps().size());
		Assert.assertEquals("map1",mr.getMap(1).getName());
			
		Assert.assertTrue(mr.createMap(1,"map2"));
		Assert.assertTrue(mr.createMap(1,"map3"));
		Assert.assertEquals(2, mr.getMap(2).getID());
			*/
			
		//		---------------- Connexion ---------------- //
		/*Map u4 = new Map(0,"Map4","psw4");
		Assert.assertEquals("This Map doesn't exist",mr.connectMap(u4));
		u1.setPassword("psw2");
		Assert.assertEquals("Mapname and password do not match",mr.connectMap(u1));
		u1.setPassword("psw1");
		Assert.assertEquals("Welcome back ^^ &/viewmap/viewmap.html?uid=1",mr.connectMap(u1));
			*/
		
		//	---------------- Edit elements ---------------- //
		/*u2.setPassword("new_psw2");
		Assert.assertEquals("Map not found!",mr.editMap(6, u1));
		Assert.assertEquals("Password successfully updated!",mr.editMap(2, u2));
		Assert.assertEquals("new_psw2",mr.getMap(2).getPassword());					
*/
			
		//	---------------- Delete elements ---------------- //
	/*	Assert.assertFalse(mr.deleteMap(8));
		Assert.assertTrue(mr.deleteMap(2));
		Assert.assertEquals(2, mr.getMaps().size());
		*/	
	}

}
