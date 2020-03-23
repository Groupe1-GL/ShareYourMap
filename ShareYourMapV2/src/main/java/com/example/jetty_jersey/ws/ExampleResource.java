/* package com.example.jetty_jersey.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import java.util.*;

@Path("/")
public class ExampleResource {

	public static List<User> users = new Arraylist<User>();
	public static List<Map> maps = new ArrayList<Map>();

	Location Mcdo = new Location("Mcdo");
	Location KFC = new Location("KFC");
	Location Quick = new Location("Quick");
	Location Monoprix = new Location("Monoprix");
	Location Carrefour = new Location("Carrefour");
	
	//Creation de map
	Map m1 = new Map();
	m1.addLocation(Mcdo);
	Map m2 = new Map();
	m2.addLocation(KFC); 
	m2.addLocation(Monoprix;)
	Map m3 = new Map();
	m3.addLocation(Carrefour); 
	
	User u1 = new User();
	u1.putMap(m1);
	u1.putMap(m2);
	users.add(u1);
	User u2 = new User();
	u2.putMap(m3);
	users.add(u2);
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/homepage")
	public List<User> getUsers() {  //liste des users
		return this.users;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/homepage")
	public List<User> addUser() {
		User u = new User();
		this.users.add(u);
		return users;
	}
	
	
	
	public List<Map> afficheListMap(User u){ //fonction qui retourne la liste de l'utilisateur u
		return u.listMap;
	}
	
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/searchmap")
	public List<Map> getMaps() {
		return maps;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchmap")
	public boolean addMap() {
		Map m = new Map();
		m.addLocation(new Location());
		m.addLocation(new Location());
		return maps.add(m);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchmap/{map.name}")
	public Map searchMapByName(@PathParam("map.name") String name) {
		Map m = new Map();
		return m;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/searchmap/{map.id}")
	public List<Location> getLocations(@PathParam("map.id") String id) {
		ArrayList<Location> locations = new ArrayList<Location>();
		locations.add(new Location());
		locations.add(new Location());
		return locations;
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}")
	public User getUser(@PathParam("user.id") int id) {
		for(User u: users) {
			if(u.getUserID()==id) {
				return u;
			}
		}
		if(id == 1) {
			return new User();
		}
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}")
	public boolean addMapOnUser(@PathParam("user.id") int uid) {
		for(User u: users) {
			if(u.getUserID()==uid) {
				return u.getMaps().add(new Map());
			}
		}
		return false;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	public List<Location> getLocationsOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid) {
		List<Location> locations = new ArrayList<Location>();

		locations.add(new Location());
		locations.add(new Location());
		locations.add(new Location());
	
		return locations;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	public boolean deleteMapOnUser(@PathParam("user.id") int uid,
								  @PathParam("map.id") int mid) {
		for (User us: users) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						return us.getMaps().remove(map);
					}
				}
			}
		}
		return false;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/viewlocation/{user.id}/{map.id}")
	public boolean addLocationsOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid) {
		Location l1 = new Location();
		for (User us: users) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						return map.addLocation(l1);
					}
				}
			}
		}
		return false;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/viewlocation/{user.id}/{map.id}/{location.id}")
	public boolean editLocationsOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid,
								        @PathParam("location.id") int lid) {
		for (User us: users) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						for (Location lo: map.getLocations()) {
							if (lo.getID() == lid) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/viewlocation/{user.id}/{map.id}/{location.id}")
	public boolean deleteLocationsOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid,
								        @PathParam("location.id") int lid) {
		for (User us: users) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						for (Location lo: map.getLocations()) {
							if (lo.getID() == lid) {
								return map.getLocations().remove(lo);
							}
						}
					}
				}
			}
		}
		return false;
	}
	
}
*/