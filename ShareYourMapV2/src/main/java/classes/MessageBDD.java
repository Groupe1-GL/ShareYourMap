package classes;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class MessageBDD {
	private String msg;
	private int lid;
	
	public MessageBDD(int lid, String msg) {
		this.msg = msg;
		this.lid = lid;
	}

	public String getMsg() {
		return msg;
	}

	public int getLid() {
		return lid;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}
	
}
