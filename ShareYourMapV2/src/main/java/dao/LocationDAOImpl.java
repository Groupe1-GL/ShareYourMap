package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import classes.Location;
import classes.Map;
import classes.User;

public class LocationDAOImpl implements LocationDAO {

	//static List<Location> l = Location.generateLocations();
	static List<Location> l = new ArrayList<Location>();
	/**
     * Creates and adds a location on a map selected by its id.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid		the map identifier 
	 * @param	lid		the location identifier
	 * @param	name	the location name
	 * @param	descr 	the location description
	 * @param	label	the location label
	 * @return			true if the operation was successful
	 */
	public boolean createLocationOnMap(	int uid, 
										int mid, 
										String name, 
										String descr, 
										String label, 
										double x, 
										double y) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						Location newLocation = new Location(name, us.getName(), x, y, descr, label);
						LocationDAOImpl.l.add(newLocation);
						ma.getLocations().add(newLocation);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
     * Contributes on the feed of a location by a message.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param  		uid		the user identifier 
	 * @param  		mid		the map identifier 
	 * @param  		lid		the location identifier 
	 * @param		message
	 * @return		true	if the operation was successful
	 */
	public boolean contributeOnLocation(int uid,
										int mid,
										int lid,
										String message) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if (lo.getID() == lid&&(lo.getCreatorName().equals(us.getName()))) {								
								return lo.addMessage(message);
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
     * Edits a location.
	 *
	 * @param	uid		the user identifier 
	 * @param	mid 	the map identifier 
	 * @param	lid 	the location identifier 
	 * @param	name 	the location name
	 * @param	descr	the location description
	 * @param	label	the location label
	 * @return			true if the operation was successful
	 */
	public boolean editLocation(		int uid,
										int mid,
										int lid,
										String name,
										String descr,
										String label) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if ((lo.getID() == lid)&&(lo.getCreatorName().equals(us.getName()))) {
								return lo.setName(name)&&lo.setDescription(descr)&&lo.setLabel(label);
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
     * Deletes a location on a map.
	 * If the map or the location doesn't exist nothing is deleted.
	 *
	 * @param  uid the user identifier 
	 * @param  mid the map identifier 
	 * @param  lid the location identifier 
	 * @return	   true if the operation was successful
	 */
	public boolean deleteLocation(		int uid,
										int mid,
										int lid) {
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if (lo.getID() == lid) {								
								ma.getLocations().remove(lo);
								//gérer l'instruction : supprimer de la base de la donnée
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	

	/**
     * Contributes on the feed of a location by a picture.
	 * If the map or the location doesn't exist nothing is added.
	 *
	 * @param  		uid		the user identifier 
	 * @param  		mid		the map identifier 
	 * @param  		lid		the location identifier 
	 * @param		path
	 * @return		true	if the operation was successful
	 */
	public boolean contributeOnLocationImg(int uid,
										int mid,
										int lid,
										InputStream uploadedInputStream,
										FormDataContentDisposition fileDetail) {
		//Save the picture on a specific folder
		String fileLocation = System.getProperty("user.dir") + "upload/img" + fileDetail.getFileName(); 
		try {  
            FileOutputStream out = new FileOutputStream(new File(fileLocation));  
            int read = 0;  
            byte[] bytes = new byte[1024];  
            out = new FileOutputStream(new File(fileLocation));  
            while ((read = uploadedInputStream.read(bytes)) != -1) {  
                out.write(bytes, 0, read);  
            }  
            out.flush();  
            out.close();  
        } catch (IOException e) {e.printStackTrace();} 
		
		
		//Picture save on database process, the path will be use to display it via JavaScript
		for (User us: UserDAOImpl.u) {
			if (us.getId() == uid) {
				for (Map ma: us.getMaps()) {
					if (ma.getID() == mid) {
						for (Location lo: ma.getLocations()) {
							if (lo.getID() == lid&&(lo.getCreatorName().equals(us.getName()))) {								
								return lo.addPicture(fileLocation);
							}
						}
					}
				}
			}
		}
		return false;
	}
	
}
