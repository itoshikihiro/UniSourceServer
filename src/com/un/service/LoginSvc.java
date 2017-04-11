    /**   
    * @Title: LoginSvc.java 
    * @Package com.un.service 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 11, 2017 1:19:08 PM 
    * @version V1.0   
    */  
package com.un.service;


    /** 
    * @ClassName: LoginSvc 
    * @Description: the interface for login service
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date Apr 11, 2017 1:19:08 PM 
    *  
    */
public interface LoginSvc {
	//check if the userID exits in this system
	public boolean checkUser(String userID);
	//check if the userID matches the user's password
	public boolean validUser(String userID, String password);
}
