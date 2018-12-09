package project1;

import java.io.Serializable;

class User implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String permission;
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

class User_Msg implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public User us;
	public String msg;
	public User_Msg(User us, String msg) {
		super();
		this.us = us;
		this.msg = msg;
	}
	
}