    /**   
    * @Title: ActivitySVC.java 
    * @Package com.un.service 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date May 2, 2017 5:39:24 PM 
    * @version V1.0   
    */  
package com.un.service;

import java.util.ArrayList;

import com.un.pojo.Activity;

/** 
    * @ClassName: ActivitySVC 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date May 2, 2017 5:39:24 PM 
    *  
    */
public interface ActivitySvc {
	public ArrayList<Activity> readAList(String userID);
	public void addActivity(String userID, Activity a);
	public void deleteActivity(String userID, Activity a);
}

