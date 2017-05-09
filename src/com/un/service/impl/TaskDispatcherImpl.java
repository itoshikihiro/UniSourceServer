/**   
 * @Title: TaskDispatcherImpl.java 
 * @Package com.un.service.impl 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date Apr 12, 2017 12:41:30 PM 
 * @version V1.0   
 */  
package com.un.service.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import com.lowagie.text.DocumentException;
import com.un.pojo.Activity;
import com.un.pojo.Course;
import com.un.pojo.Message;
import com.un.pojo.Student;
import com.un.pojo.User;
import com.un.service.ActivitySvc;
import com.un.service.CoursesSvc;
import com.un.service.LoginSvc;
import com.un.service.RegisterSvc;
import com.un.service.StuProfileSvc;
import com.un.service.TaskDispatchSvc;
import com.un.tool.PDFGenerator;
import com.un.tool.UserListReader;

/** 
 * @ClassName: TaskDispatcherImpl 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date Apr 12, 2017 12:41:30 PM 
 *  
 */
public class TaskDispatcherImpl implements TaskDispatchSvc{

	LoginSvc loginService = null;
	Socket client = null;
	ObjectOutputStream objectWriter = null;
	RegisterSvc registerService = null;
	CoursesSvc courseService = null;
	String userID = null;
	ActivitySvc activityService = null;
	StuProfileSvc stuProfileService = null;

	/** 
	 * <p>Description: Constructor </p>  
	 */
	public TaskDispatcherImpl(Socket client, ObjectOutputStream objectWriter) {
		loginService = new LoginSvcImpl();
		this.client = client;
		this.objectWriter = objectWriter;
		registerService = new RegisterSvcImpl();
		courseService = new CourseSvcImpl();
		activityService = new ActivitySvcImpl();
		stuProfileService = new StuProfileSvcImpl();
	}

	/* (non Javadoc) 
	 * <p>Title: dispath</p> 
	 * <p>Description: use message to determine which service it goes to</p> 
	 * @param m 
	 * @see com.un.service.TaskDispatchSvc#dispath(com.un.pojo.Message) 
	 */ 
	@Override
	public void dispatch(Message m) {
		try {
			if(m.getTaskCode()==-1){
				returnErrorMes();
			}else{
				switch (m.getTaskCode()) {
				case 0:
					loginCheck(m);
					break;
				case 1:
					requestRegister(m);
					break;
				case 2:
					requestCList(m);
					break;
				case 31:
					userID = null;
					recordUserID(m);
					break;
				case 32:
					addNewCourse(m);
					break;
				case 4: 
					requestAList(m);
					break;
				case 50:
					userID = null;
					recordUserID(m);
					break;
				case 51:
					addNewActivity(m);
					break;
				case 52:
					deleteActivity(m);
					break;
				case 53:
					updateActivity(m);
					break;
				case 12:
					updateStuPro(m);
					break;
				case 81:
					requestResume(m);
					break;
				case 82:
					sendFile(m);
					break;
				default:
					returnErrorMes();
					break;
				}
			}
		} 
		catch (Exception e) //if it catch any kind of faults return error message
		{
			returnErrorMes();
		}
	}

	public void loginCheck(Message m) throws IOException{
		User u = (User)m.getObject();
		//print the object we got from the client
		System.out.println(u.toString());
		//to check if there exists such user in the system
		if(!loginService.validUser(u.getRoleID(), u.getUserID(), u.getPassword()))
		{
			returnErrorMes();
			return;
		}
		switch (u.getRoleID()) {
		case 0:
			returnMes(new Message(0,loginService.getUserProfile(u.getUserID())));
			break;
		case 1:
			returnMes(new Message(0,loginService.getStuProfile(u.getUserID())));
			break;
		default:
			returnErrorMes();
			break;
		}

	}

	public void returnMes(Message m) throws IOException{
		objectWriter.writeObject(m);
		objectWriter.flush();
	}


	//for any error send code -1 with false to indicate there is no object in message
	public void returnErrorMes(){
		Message rem = new Message(-1,null);
		try {
			objectWriter.writeObject(rem);
			objectWriter.flush();
		} catch (IOException e) {
			System.out.println("Error for sending object to client");
		}
	}

	//to invoke the register service's function
	public void requestRegister(Message m)
	{
		String s = (String)m.getObject();
		//print the object we got from the client
		//System.out.println(s.toString());
		registerService.requestRegister(s);
		try {
			returnMes(new Message(1,"Your information has sent to one of admins, please check your email box recently. The admin will send you an email to confirm your information"));
		} catch (IOException e) {
			System.out.println("Error for sending object to client");
		}
	}


	//use course service to get course list and return back to client
	public void requestCList(Message m)
	{
		Student s = (Student) m.getObject();
		ArrayList<Course> ac = courseService.readCList(s.getUserID());
		try {
			returnMes(new Message(2,ac));
		} catch (IOException e) {
			System.out.println("Error for sending object to client");
		}
	}

	//for adding a new course to the user course list, we need to record the user's ID at first
	public void recordUserID(Message m)
	{		
		Student s = (Student)m.getObject();
		this.userID = s.getUserID();
	}

	//clients can add a new course, but not change any course information 
	public void addNewCourse(Message m)
	{
		Course c = (Course) m.getObject();
		courseService.addCourse(userID, c);
		//then return user's course list
		ArrayList<Course> ac = courseService.readCList(userID);
		try {
			returnMes(new Message(32,ac));
		} catch (IOException e) {
			System.out.println("Error for sending object to client");
		}
	}

	//send client's activity list to client side
	public void requestAList(Message m)
	{
		Student s = (Student) m.getObject();
		ArrayList<Activity> aa = activityService.readAList(s.getUserID());
		try {
			returnMes(new Message(4,aa));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error for sending object to client");
		}
	}

	//add a new activity
	public void addNewActivity(Message m)
	{
		Activity a = (Activity) m.getObject();
		activityService.addActivity(userID, a);
		//then return user's course list
		ArrayList<Activity> aa = activityService.readAList(userID);
		try {
			returnMes(new Message(51,aa));
		} catch (IOException e) {
			System.out.println("Error for sending object to client");
		}
	}

	//client can delete activity
	public void deleteActivity(Message m) throws FileNotFoundException
	{
		Activity a = (Activity) m.getObject();
		activityService.deleteActivity(userID, a);
		//then return user's course list
		ArrayList<Activity> aa = activityService.readAList(userID);
		try {
			returnMes(new Message(51,aa));
		} catch (IOException e) {
			System.out.println("Error for sending object to client");
		}
	}
	
	//for student to update their activities
	public void updateActivity(Message m) throws FileNotFoundException{
		Activity a = (Activity) m.getObject();
		activityService.deleteActivity(userID, a);
		addNewActivity(m);
	}
	
	//a function to update student's information, but for client, they are not allowed
	//to change userID
	public void updateStuPro(Message m) throws IOException{
		Student s =(Student) m.getObject();
		stuProfileService.updateStuProfile(s);
		UserListReader ulr = new UserListReader();
		ArrayList<Student> as = ulr.getStudentList();
		Iterator<Student> i = as.iterator();
		while(i.hasNext())
		{
			Student tems = i.next();
			if(tems.getUserID().equals(s.getUserID()))
			{
				returnMes(new Message(12,tems));
			}
		}
	}
	
	//It generates sutdent's resume and store in the server. 
	public void requestResume(Message m) throws DocumentException, IOException
	{
		Student s = (Student) m.getObject();
		PDFGenerator.generate(s.getUserID());
	}

	
	//send file to buffer that client can receive it
	public void sendFile(Message m) throws IOException
	{
		FileInputStream fis=null;
		DataOutputStream dos=null;
		Student s = (Student) m.getObject();
		File file = new File("./Content/files/"+s.getUserID()+"Resume.pdf");
		try{
			fis = new FileInputStream(file);
			dos = new DataOutputStream(client.getOutputStream());

			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();

			byte[] sendBytes = new byte[1024];
			int length = 0;
			while((length = fis.read(sendBytes, 0, sendBytes.length)) > 0){
				dos.write(sendBytes, 0, length);
				dos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis != null)
				fis.close();
			if(dos != null)
				dos.close();
		}
	}
}

