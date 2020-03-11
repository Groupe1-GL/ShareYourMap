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
	static List<Map> l = new ArrayList<Location>();
	
	
	//manipulation générale sur les users (infos, ajouter, supprimer)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users")
	public List<User> getUsers() {
		return u;
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/users")
	public void addUser() {
		//voir pour ajouter paramBody 
		u.add(new User());
	}
	//actions particulières sur les users
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user.id}")
	public User getUser(@PathParam("user.id") int uid) {
		for (User us: u) {
			if (us.getUserID() == uid)
				return us;
		}
		return null;
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user.id}")
	public void editUser(@PathParam("user.id") int uid) {
		//voir pour ajouter paramBody 
		for (User us: u) {
			if (us.getUserID() == uid);
				//TODO
		}
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{user.id}")
	public boolean delUser(@PathParam("user.id") int uid) {
		for (User us: u) {
			if (us.getUserID() == uid) {
				u.remove(us);
				return true;
			}
		}
		return false;
	}
	
	
	//actions générales sur les maps
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/maps")
	public List<Map> getMaps() {
		return m;
	}
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps")
	public void addMap() {
		//voir pour ajouter paramBody 
		m.add(new Map());
	}
	//actions particulières sur les maps
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/maps/{map.id}")
	public Map getMap(@PathParam("map.id") int mid) {
		for (Map map: m) {
			if (map.getID() == mid) {
				return map;
			}
		}
		return null;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/maps/{map.id}")
	public void editMap(@PathParam("map.id") int mid) {
		//voir si on peut modifier une map localement ou globalement
		for (Map map: m) {
			if (map.getID() == mid) {
				//TODO
			}
		}
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/maps/{map.id}")
	public boolean delMap(@PathParam("map.id") int mid) {
		//voir si on peut modifier une map localement ou globalement
		for (Map ma: m) {
			if (ma.getID() == mid) {
				m.remove(ma);
				return true;
			}
		}
		return false;
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
				for (Map map: m) {
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
