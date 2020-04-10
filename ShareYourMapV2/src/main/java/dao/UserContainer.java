package dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import classes.User;


@PersistenceCapable
public class UserContainer {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)//
	protected Long id = null;
	
	@Persistent
	protected List<User> users = null;

	public UserContainer() {
		super();
		this.users = new ArrayList<User>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> Users) {
		this.users = users;
	}
}
