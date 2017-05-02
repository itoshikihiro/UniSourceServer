    /**   
    * @Title: ActivitySvcImpl.java 
    * @Package com.un.service.impl 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date May 2, 2017 5:42:48 PM 
    * @version V1.0   
    */  
package com.un.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.un.pojo.Activity;
import com.un.service.ActivitySvc;

/** 
    * @ClassName: ActivitySvcImpl 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date May 2, 2017 5:42:48 PM 
    *  
    */
public class ActivitySvcImpl implements ActivitySvc {

	final public static String FILEADD = "./Content/files/";
	final public static String SUFFIX = "Activity.csv";
	
	/* (non Javadoc) 
	* <p>Title: readCList</p> 
	* <p>Description: </p> 
	* @param userID
	* @return 
	* @see com.un.service.ActivitySvc#readCList(java.lang.String) 
	*/ 
	@Override
	public ArrayList<Activity> readAList(String userID) {
		ArrayList<Activity> rev = new ArrayList<Activity>();
		
		File f = new File(FILEADD+userID+SUFFIX);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// read one line at a time
			while ((tempString = reader.readLine()) != null) {
				//a test method
				//System.out.println(tempString);
				String[]temp=tempString.split(",");
				Activity c = new Activity(temp[0],temp[1]);
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

	/* (non Javadoc) 
	* <p>Title: addActivity</p> 
	* <p>Description: </p> 
	* @param userID
	* @param a 
	* @see com.un.service.ActivitySvc#addActivity(java.lang.String, com.un.pojo.Activity) 
	*/ 
	@Override
	public void addActivity(String userID, Activity a) {
		// TODO Auto-generated method stub
		
	}

	/* (non Javadoc) 
	* <p>Title: deleteActivity</p> 
	* <p>Description: </p> 
	* @param userID
	* @param a 
	* @see com.un.service.ActivitySvc#deleteActivity(java.lang.String, com.un.pojo.Activity) 
	*/ 
	@Override
	public void deleteActivity(String userID, Activity a) {
		// TODO Auto-generated method stub
		
	}

}

