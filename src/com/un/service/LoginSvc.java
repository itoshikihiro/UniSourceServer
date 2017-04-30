    /**   
    * @Title: LoginSvc.java 
    * @Package com.un.service 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 11, 2017 1:19:08 PM 
    * @version V1.0   
    */  
package com.un.service;

import com.un.pojo.Student;
import com.un.pojo.User;

/** 
    * @ClassName: LoginSvc 
    * @Description: the interface for login service
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date Apr 11, 2017 1:19:08 PM 
    *  
    */
public interface LoginSvc {
	//check if the userID matches the user's password
	public boolean validUser(int roleID, String userID, String password);
	//get user profile profile
	public User getUserProfile(String userID);
	//get student profile profile
	public Student getStuProfile(String userID);
}

