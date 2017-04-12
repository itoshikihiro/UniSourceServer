/**   
 * @Title: UserListReader.java 
 * @Package com.un.tool 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date Apr 11, 2017 5:02:35 PM 
 * @version V1.0   
 */  
package com.un.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.un.pojo.Student;
import com.un.pojo.User;

/** 
 * @ClassName: UserListReader 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date Apr 11, 2017 5:02:35 PM 
 *  
 */

public class UserListReader {
	public static HashMap<Integer,String> userMap =null;
	final public static String FILEADD = "./Content/files/";
	final public static String SUFFIX = ".csv";

	public UserListReader(){
		userMap = new HashMap<Integer, String>();
		File f = new File(FILEADD+"userMap"+SUFFIX);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// read one line at a time
			while ((tempString = reader.readLine()) != null) {
				//a test method
				//System.out.println(tempString);
				String[]temp=tempString.split(",");
				userMap.put(Integer.parseInt(temp[0]), temp[1]);
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
	}

	//get admin list
	public ArrayList<User> getAdminList(){
		String fileName = userMap.get(0);
		ArrayList<User> rev = new ArrayList<User>();
		File f = new File(FILEADD+fileName);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// read one line at a time
			while ((tempString = reader.readLine()) != null) {
				//a test method
				//System.out.println(tempString);
				String[]temp=tempString.split(",");
				//create an student object
				User s = new User(temp[0],temp[1],temp[2],Integer.parseInt(temp[3]));
				//add to array list
				rev.add(s);
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

	//get student list
	public ArrayList<Student> getStudentList(){
		String fileName = userMap.get(1);
		ArrayList<Student> rev = new ArrayList<Student>();
		File f = new File(FILEADD+fileName);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// read one line at a time
			while ((tempString = reader.readLine()) != null) {
				//a test method
				//System.out.println(tempString);
				String[]temp=tempString.split(",");
				//create an student object
				Student s = new Student(temp[0],temp[1],temp[2],Integer.parseInt(temp[3]),Integer.parseInt(temp[4]),Integer.parseInt(temp[5]),temp[6]);
				//add to array list
				rev.add(s);
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
	
	public HashMap<String,String> getNameAndPass(ArrayList<?> list){
		HashMap<String, String> rev = new HashMap<String,String>();
		for(Object o:list){
			User u = (User) o;
			rev.put(u.getUserID(), u.getPassword());
		}
		return rev;
	}
}

