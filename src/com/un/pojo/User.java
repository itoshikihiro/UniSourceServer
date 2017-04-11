/**
 *  Copyright © 2017company name. All rights reserved.

 *

 * @Title: User.java

 * @Prject: UniSource

 * @Package: com.unisource.pojo

 * @Description: TODO

 * @author: Dell  

 * @date: Apr 10, 2017 3:38:16 PM

 * @version: V1.0  
 */
package com.un.pojo;

/**
 * @ClassName: User

 * @Description: TODO

 * @author: Jie Lin

 * @date: Apr 10, 2017 3:38:16 PM

 */
public class User {
	protected int userID;//email will be used
	protected String username;//the actual name will be used
	protected String password;
	protected String description;
	protected int roleID;
	
	//regular constructor
	public User(){}

	/**
	 * @Title:User
	
	 * @Description:TODO
	
	 * @param userID
	 * @param username
	 * @param password
	 * @param description
	 * @param roleID
	 */
	public User(int userID, String username, String password,
			String description, int roleID) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.description = description;
		this.roleID = roleID;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the roleID
	 */
	public int getRoleID() {
		return roleID;
	}

	/**
	 * @param roleID the roleID to set
	 */
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	/* (non Javadoc)
	
	 * @Title: toString
	
	 * @Description: TODO
	
	 * @return
	
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username
				+ ", password=" + password + ", description=" + description
				+ ", roleID=" + roleID + "]";
	}
	
	
}
