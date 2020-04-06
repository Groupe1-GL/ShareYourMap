package jetty_server.ws.bouchons;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import classes.Location;
import classes.Map;
import classes.User;

import java.util.*;

@Path("/")
public class ExampleResource {

	public static List<User> users = new ArrayList<User>();
	public static List<Map> maps = Map.generateMaps2();
	public static User us = User.getUser();

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/homepage")
	public List<User> getUsers() {  //liste des users
		return ExampleResource.users;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/homepage")
	public List<User> addUser() {
		User u = new User(" "," ");
		ExampleResource.users.add(u);
		return users;
	}
	
	public List<Map> afficheListMap(User u){ //fonction qui retourne la liste de l'utilisateur u
		return u.getMaps();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/searchmap")
	public List<Map> getMaps() {
		return maps;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchmap/{map.name}")
	public Map searchMapByName(@PathParam("map.name") String name) {
		Map m = new Map("SearchMap","NoOne");
		return m;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/searchmap/{map.id}")
	public List<Location> getLocations(@PathParam("map.id") String id) {
		int i = Integer.parseInt(id) - 1;
		Map m = maps.get(i);
		return m.getLocations();
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}")
	public User getUser(@PathParam("user.id") int id) {
		if(id == 1) {
			return ExampleResource.us;
		}
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}")
	public boolean addMapOnUser(@PathParam("user.id") int uid) {
		if(uid == 1) {
			ExampleResource.us.putMap(new Map("New Map","David"));
			return true;
		}
		return false;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	public Map getUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid) {
		return ExampleResource.us.getMaps().get(mid-1);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/{user.id}/{map.id}")
	public boolean deleteMapOnUser(@PathParam("user.id") int uid,
								  @PathParam("map.id") int mid) {
		return ExampleResource.us.getMaps().remove(mid-1) != null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/viewlocation/{user.id}/{map.id}/{location.id}")
	public Location getLocationsOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid,
											@PathParam("location.id") int lid){
		return ExampleResource.us.getMaps().get(mid-1).getLocations().get(lid-1);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/viewmap/viewlocation/{user.id}/{map.id}")
	public boolean addLocationsOnUserMap(@PathParam("user.id") int uid,
								          @PathParam("map.id") int mid) {
		Location l1 = new Location("New Location","David",201,300,"Description"," ");
		return ExampleResource.us.getMaps().get(mid-1).addLocation(l1);
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
		return ExampleResource.us.getMaps().get(mid-1).getLocations().remove(lid-1) != null;
	}
	
}
