    /**   
    * @Title: TaskDispatcherImpl.java 
    * @Package com.un.service.impl 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 12, 2017 12:41:30 PM 
    * @version V1.0   
    */  
package com.un.service.impl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.un.pojo.Course;
import com.un.pojo.Message;
import com.un.pojo.Student;
import com.un.pojo.User;
import com.un.service.CoursesSvc;
import com.un.service.LoginSvc;
import com.un.service.RegisterSvc;
import com.un.service.TaskDispatchSvc;

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
	int counter = 0;
	
	/** 
	* <p>Description: Constructor </p>  
	*/
	public TaskDispatcherImpl(Socket client, ObjectOutputStream objectWriter) {
		loginService = new LoginSvcImpl();
		this.client = client;
		this.objectWriter = objectWriter;
		registerService = new RegisterSvcImpl();
		courseService = new CourseSvcImpl();
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
				case 3:
					if(counter == 0)
					{
						recordUserID(m);
						counter++;
					}else
					{
						addNewCourse(m);
						counter = 0;
						userID = null;
					}
					break;
				default:
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			System.out.println("Error for sending object to client");
		}
	}
	
	//for adding a new course to the user course list, we need to record the user's ID at first
	public void recordUserID(Message m)
	{		
		Student s = (Student)m.getObject();
		this.userID = s.getUserID();
	}
	
	public void addNewCourse(Message m)
	{
		Course c = (Course) m.getObject();
		courseService.addCourse(userID, c);
		//then return user's course list
		ArrayList<Course> ac = courseService.readCList(userID);
		try {
			returnMes(new Message(3,ac));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error for sending object to client");
		}
	}
}

