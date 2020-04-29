package classes;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class PictureBDD {
	private String pix;
	private int lid;
	
	public PictureBDD(int lid, String pix) {
		this.pix = pix;
		this.lid = lid;
	}

	public String getPix() {
		return pix;
	}

	public int getLid() {
		return lid;
	}

	public void setPix(String pix) {
		this.pix = pix;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}
}
