    /**   
    * @Title: CoursesSvc.java 
    * @Package com.un.service 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date May 2, 2017 12:39:03 AM 
    * @version V1.0   
    */  
package com.un.service;

import java.util.ArrayList;

import com.un.pojo.Course;

/** 
    * @ClassName: CoursesSvc 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date May 2, 2017 12:39:03 AM 
    *  
    */
public interface CoursesSvc {
	public ArrayList<Course> readCList(String userID);
}

