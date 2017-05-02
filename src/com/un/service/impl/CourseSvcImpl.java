    /**   
    * @Title: CourseSvcImpl.java 
    * @Package com.un.service.impl 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date May 2, 2017 1:10:23 AM 
    * @version V1.0   
    */  
package com.un.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.un.pojo.Course;
import com.un.service.CoursesSvc;

/** 
    * @ClassName: CourseSvcImpl 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date May 2, 2017 1:10:23 AM 
    *  
    */
public class CourseSvcImpl implements CoursesSvc {

	
	final public static String FILEADD = "./Content/files/";
	final public static String SUFFIX = ".csv";
	/* (non Javadoc) 
	* <p>Title: readCList</p> 
	* <p>Description: </p> 
	* @param userID
	* @return 
	* @see com.un.service.CoursesSvc#readCList(java.lang.String) 
	*/ 
	@Override
	public ArrayList<Course> readCList(String userID) {
		ArrayList<Course> rev = new ArrayList<Course>();
		
		File f = new File(FILEADD+userID+"Courses"+SUFFIX);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// read one line at a time
			while ((tempString = reader.readLine()) != null) {
				//a test method
				//System.out.println(tempString);
				String[]temp=tempString.split(",");
				Course c = new Course(temp[0],temp[1]);
				rev.add(c);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		
		return rev;
	}
	
	public void addCourse(String userID, Course c)
	{
		try
		{
			//use this constructor means if there exists such file
			//directly add more String at the end of this file
			 FileWriter writer=new FileWriter(FILEADD+userID+"Courses"+SUFFIX,true);
			 writer.write(c.getCourseCode()+","+c.getCourseName()+"\n");
			 writer.close();
		}
		catch(IOException e)//if it catches any exception
		{
			System.out.println("Can not output as a file");
		}
	}

}

