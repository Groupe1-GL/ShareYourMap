package dao;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import classes.Map;
import classes.User;

/**
 * MapDAOImpl is the implementation of the MapDAO interface used for debugging purposes.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
public class MapDAOImpl implements MapDAO {
	
	static List<Map> m = Map.generateMaps2();
	
	public List<Map> getMaps(){
		return MapDAOImpl.m;
	}
	
	public Map getMap(int mid) {
		for (Map ma : MapDAOImpl.m) {
			if (ma.getID() == mid) {
				return ma;
			}
		}
		return null;
	}
	
	public Response getSharedMap(int uid, int mid, String sharedID) {
		for (Map ma : MapDAOImpl.m) {
			if ((ma.getID() == mid)&&ma.getAccess()&&ma.getSharedID().equals(sharedID)) {
				return Response.status(Response.Status.SEE_OTHER)
				         .header(HttpHeaders.LOCATION, "/sharemap/sharemap.html?id=" + mid + "&shared-id=" + sharedID)
				         .header("X-Foo", "bar")
				         .build();
			}
		}
		 return Response
				 	.status(401)
		            .entity("Map id and Shared id do not match")
		            .build();
	}
	
	public boolean createMap(int uid, String name, boolean b) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				Map newMap = new Map(name, us.getName(), b);
				us.getMaps().add(newMap);
				MapDAOImpl.m.add(newMap);
				return true;
			}
		}
		return false;
	}
	
	public boolean editMap(int uid, int mid, String name, boolean access) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma : m) {
					if ((ma.getID() == mid)&&(uid == us.getId())) {
						return ma.setName(name)&&ma.setAccess(access);
					}
				}
			}
		}
		return false;
	}
	
	public boolean deleteMap(int uid, int mid) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma : m) {
					if ((ma.getID() == mid)&&(ma.getCreatorName() == us.getName())) {
						us.getMaps().remove(ma);
						return m.remove(ma);
					}
				}
			}
		}
		return false;
	}
}
