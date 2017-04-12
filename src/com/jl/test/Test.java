/**   
 * @Title: Test.java 
 * @Package com.jl.test 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date Apr 11, 2017 7:22:02 PM 
 * @version V1.0   
 */  
package com.jl.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.un.pojo.Student;

/** 
 * @ClassName: Test 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date Apr 11, 2017 7:22:02 PM 
 *  
 */
public class Test {
	public static HashMap<Integer,String> userMap = null;
	final public static String FILEADD = "./Content/files/";
	final public static String SUFFIX = ".csv";
	public static enum roleNumber{
		ADMIN,STUDENT,TEACHER,STAFF
	}
	
	public static void main(String[] args) {
		ArrayList<Student> rev = new ArrayList<Student>();
		userMap=new HashMap<Integer, String>();
		File f = new File(FILEADD+"userMap"+SUFFIX);
		BufferedReader reader = null;
		try{
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println(tempString);
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
		
		String studentFileName = userMap.get(1);
		f = new File(FILEADD+studentFileName);
		reader = null;
		try{
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(f));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println(tempString);
				String[]temp=tempString.split(",");
				Student s = new Student(temp[0],temp[1],temp[2],Integer.parseInt(temp[3]),Integer.parseInt(temp[4]),Integer.parseInt(temp[5]),temp[6]);
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
		
		for(Student s:rev){
			System.out.println(s);
		}
	}
}

