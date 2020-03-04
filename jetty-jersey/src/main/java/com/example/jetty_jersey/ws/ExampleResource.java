package com.example.jetty_jersey.ws;

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
	
	static List<User> u = new ArrayList<User>();
	static List<Map> m = new ArrayList<Map>();
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/homepage")
	public List<User> getUsers() {
		return u;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/homepage")
	public List<User> addUser() {
		u.add(new User());
		return u;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/searchmap")
	public List<Map> getMaps() {
		return m;
	}
	
	//pas dans le doc, besoin pour ajouter des maps
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchmap")
	public List<Map> addMap() {
		m.add(new Map());
		return m;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/searchmap/{map.id}")
	public List<Location> getLocations(@PathParam("map.id") int id) {
		for (Map map: m) {
			if (map.getID() == id) {
				return map.getLocations();
			}
		}
		return null;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchmap/{map.id}")
	public List<Location> addLocation (@PathParam("map.id") int id) {
		Location l1 = new Location();
		for (Map map: m) {
			if (map.getID() == id) {
				map.addLocation(l1);
				return map.getLocations();
			}
		}
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}")
	public List<Map> getUserMaps(@PathParam("user.id") int id) {
		for (User us: u) {
			if (us.getUserID() == id) {
				return us.getMaps();
			}
		}
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	//va ajouter une map existante sur un user
	public List<Map> addMapOnUser(@PathParam("user.id") int uid,
								  @PathParam("map.id") int mid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						us.getMaps().add(map);
						return us.getMaps();
					}
				}
			}
		}
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	public List<Location> getLocsOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						return map.getLocations();
					}
				}
			}
		}
		return null;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	//va ajouter une map existante sur un user
	public List<Map> delMapOnUser(@PathParam("user.id") int uid,
								  @PathParam("map.id") int mid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						us.getMaps().remove(map);
						return us.getMaps();
					}
				}
			}
		}
		return null;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	public List<Location> addLocOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid) {
		Location l1 = new Location();
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						map.addLocation(l1);
						return map.getLocations();
					}
				}
			}
		}
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewlocation/{user.id}/{map.id}/{location.name}")
	public Location getLocOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid,
								        @PathParam("location.name") int lid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						for (Location lo: map.getLocations()) {
							if (lid == lo.getID()) return lo;
						}
					}
				}
			}
		}
		return null;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewlocation/{user.id}/{map.id}/{location.name}")
	public List<Location> delLocOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid,
								        @PathParam("location.name") int lid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				for (Map map: us.getMaps()) {
					if (map.getID() == mid) {
						for (Location lo: map.getLocations()) {
							if (lo.getID() == lid) {
								map.getLocations().remove(lo);
								return map.getLocations();
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	
	
}
