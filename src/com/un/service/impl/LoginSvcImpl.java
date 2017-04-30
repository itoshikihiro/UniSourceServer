/**   
 * @Title: LoginSvcImpl.java 
 * @Package com.un.service.impl 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date Apr 11, 2017 1:19:39 PM 
 * @version V1.0   
 */  
package com.un.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.un.pojo.Student;
import com.un.pojo.User;
import com.un.service.LoginSvc;
import com.un.tool.UserListReader;

/** 
 * @ClassName: LoginSvcImpl 
 * @Description: the implements of login service
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date Apr 11, 2017 1:19:39 PM 
 *  
 */
public class LoginSvcImpl implements LoginSvc{

	/* (non Javadoc) 
	 * <p>Title: validUser</p> 
	 * <p>Description: </p> 
	 * @param userID
	 * @param password
	 * @return 
	 * @see com.un.service.LoginSvc#validUser(java.lang.String, java.lang.String) 
	 */ 
	private UserListReader ulr = null;
	
	//constructor
	public LoginSvcImpl(){
		ulr=new UserListReader();
	}
	
	@Override
	public boolean validUser(int roleID,String userID, String password) {
		boolean rev = false;
		HashMap<String,String> nameAndPass = new HashMap<String,String>();
		ArrayList<Student> stuList = null;
		ArrayList<User> userList = null;

		switch (roleID) {
		case 0:
			userList = ulr.getAdminList();
			break;
		case 1:
			stuList =ulr.getStudentList();
			break;
		default:
			break;
		}
		
		if(userList!=null)
		{
			nameAndPass = ulr.getNameAndPass(userList);
		}
		else if(stuList != null)
		{
			nameAndPass = ulr.getNameAndPass(stuList);
		}
		else
		{
			return false;
		}
		//if there is such user in the system
		if(nameAndPass.containsKey(userID))
		{
			//if the user's password matches
			if(nameAndPass.get(userID).matches(password))
			{
				rev = true;
			}
		}
		return rev;
	}

	/* (non Javadoc) 
	* <p>Title: getUserProfile</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.un.service.LoginSvc#getUserProfile() 
	*/ 
	@Override
	public User getUserProfile(String userID) {
		ArrayList<User> al = ulr.getAdminList();
		Iterator<User> i = al.iterator();
		while(i.hasNext()){
			User u = i.next();
			if(u.getUserID().equals(userID)){
				return u;
			}
		}
		return null;
	}

	/* (non Javadoc) 
	* <p>Title: getStuProfile</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.un.service.LoginSvc#getStuProfile() 
	*/ 
	@Override
	public Student getStuProfile(String userID) {
		ArrayList<Student> al = ulr.getStudentList();
		Iterator<Student> i = al.iterator();
		while(i.hasNext()){
			Student u = i.next();
			if(u.getUserID().equals(userID)){
				return u;
			}
		}
		return null;
	}

}

