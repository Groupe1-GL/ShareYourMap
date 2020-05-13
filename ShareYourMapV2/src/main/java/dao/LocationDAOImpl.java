package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import classes.Location;
import classes.Map;
import classes.User;

/**
 * LocationDAOImpl is the implementation of the LocationDAO interface used for debugging purposes.
 *
 * @author Mohamed Ahmed
 * @version %I%, %G%
 * @since 1.0
 */
public class LocationDAOImpl implements LocationDAO {

	static List<Location> l = Location.generateLocations();
	
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
