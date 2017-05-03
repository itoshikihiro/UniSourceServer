/**   
 * @Title: StuProfileSvcImpl.java 
 * @Package com.un.service.impl 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date May 2, 2017 10:36:51 PM 
 * @version V1.0   
 */  
package com.un.service.impl;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.un.pojo.Student;
import com.un.service.StuProfileSvc;
import com.un.tool.UserListReader;

/** 
 * @ClassName: StuProfileSvcImpl 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date May 2, 2017 10:36:51 PM 
 *  
 */
public class StuProfileSvcImpl implements StuProfileSvc {
	final public static String FILEADD = "./Content/files/";
	final public static String SUFFIX = "studentList.csv";
	/* (non Javadoc) 
	 * <p>Title: addStuProfile</p> 
	 * <p>Description: </p> 
	 * @param s 
	 * @see com.un.service.StuProfileSvc#addStuProfile(com.un.pojo.Student) 
	 */ 
	@Override
	public void addStuProfile(Student s) {
		try
		{
			//use this constructor means if there exists such file
			//directly add more String at the end of this file
			FileWriter writer=new FileWriter(FILEADD+SUFFIX,true);
			writer.write(s.getUserID()+",");
			if(s.getUsername().equals(null))
			{
				writer.write(",");
			}
			else
			{
				writer.write(s.getUsername()+",");
			}
			writer.write(s.getPassword()+",");
			writer.write("1,");

			if(s.getGraduateYear()==0)
			{
				writer.write(",");
			}
			else
			{
				writer.write(s.getGraduateYear()+",");
			}
			if(s.getAge()==0)
			{
				writer.write(",");
			}
			else
			{
				writer.write(s.getAge()+",");
			}
			if(s.getDescription().equals(null))
			{
				writer.write(" \n");
			}
			else
			{
				writer.write(s.getDescription()+"\n");
			}
			writer.close();
		}
		catch(IOException e)//if it catches any exception
		{
			System.out.println("Can not output as a file");
		}
	}

	/* (non Javadoc) 
	 * <p>Title: deleteStuProfile</p> 
	 * <p>Description: </p> 
	 * @param s 
	 * @see com.un.service.StuProfileSvc#deleteStuProfile(com.un.pojo.Student) 
	 */ 
	@Override
	public void deleteStuProfile(Student s) throws FileNotFoundException {
		UserListReader ulr = new UserListReader();
		ArrayList<Student> as = ulr.getStudentList();
		for(int i = 0; i<as.size();i++)
		{
			if(as.get(i).getUserID().equals(s.getUserID()))
			{
				as.remove(i);
				reWrite(as);
				return;
			}
		}
		throw new FileNotFoundException();
	}

	/* (non Javadoc) 
	 * <p>Title: updateStuProfile</p> 
	 * <p>Description: </p> 
	 * @param s 
	 * @see com.un.service.StuProfileSvc#updateStuProfile(com.un.pojo.Student) 
	 */ 
	@Override
	public void updateStuProfile(Student s) throws FileNotFoundException {
		UserListReader ulr = new UserListReader();
		ArrayList<Student> as = ulr.getStudentList();
		for(int i = 0; i<as.size();i++)
		{
			if(as.get(i).getUserID().equals(s.getUserID()))
			{
				as.remove(i);
				as.add(i, s);
				reWrite(as);
				return;
			}
		}
		throw new FileNotFoundException();
	}

	//rewrite the file
	private void reWrite(ArrayList<Student> list)
	{
		try
		{
			//使用这个构造函数时，如果存在kuka.txt文件，
			//则先把这个文件给删除掉，然后创建新的kuka.txt
			FileWriter writer=new FileWriter(FILEADD+SUFFIX);
			Iterator<Student> i = list.iterator();
			while(i.hasNext())
			{
				Student s = i.next();
				writer.write(s.getUserID()+",");
				if(s.getUsername().equals(null))
				{
					writer.write(",");
				}
				else
				{
					writer.write(s.getUsername()+",");
				}
				writer.write(s.getPassword()+",");
				writer.write("1,");

				if(s.getGraduateYear()==0)
				{
					writer.write(",");
				}
				else
				{
					writer.write(s.getGraduateYear()+",");
				}
				if(s.getAge()==0)
				{
					writer.write(",");
				}
				else
				{
					writer.write(s.getAge()+",");
				}
				if(s.getDescription().equals(null))
				{
					writer.write(" \n");
				}
				else
				{
					writer.write(s.getDescription()+"\n");
				}
			}
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}

