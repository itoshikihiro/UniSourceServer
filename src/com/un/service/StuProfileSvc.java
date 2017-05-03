    /**   
    * @Title: ProfileSvc.java 
    * @Package com.un.service 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date May 2, 2017 10:34:37 PM 
    * @version V1.0   
    */  
package com.un.service;

import java.io.FileNotFoundException;

import com.un.pojo.Student;

/** 
    * @ClassName: ProfileSvc 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date May 2, 2017 10:34:37 PM 
    *  
    */
public interface StuProfileSvc {
	public void addStuProfile(Student s);
	public void deleteStuProfile(Student s)throws FileNotFoundException;
	public void updateStuProfile(Student s)throws FileNotFoundException;
	public Student getStuProfile(String s);
}

