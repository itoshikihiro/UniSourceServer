/**   
 * @Title: Client.java 
 * @Package com.un.main 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date Apr 11, 2017 3:13:18 PM 
 * @version V1.0   
 */  
package com.un.main;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.un.pojo.Message;
import com.un.pojo.Student;
import com.un.pojo.User;

/** 
 * @ClassName: Client 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date Apr 11, 2017 3:13:18 PM 
 *  
 */
public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			//time out for 60s
			//socket.setSoTimeout(60000);
			
			//send object to client
			ObjectOutputStream objectWriter= new ObjectOutputStream(socket.getOutputStream());
			//get object from client
			ObjectInputStream objectReader = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			
			System.out.println("Get the connection");
			
			//test the correct match
			User u = new User();
			u.setPassword("a1234567890");
			u.setUserID("jabigelow@ursinus.edu");
			u.setRoleID(1);
			u.setUsername(null);
			
			objectWriter.writeObject(new Message(0,u));
			objectWriter.flush();//send object to server
			
			System.out.println("object has sent to server");
			
			Object obj = objectReader.readObject();
			if(obj!=null){
 				Message m = (Message) obj;
 				if(m.getTaskCode()==-1){
 					System.out.println("something goes wrong");
 				}else{
 					Student s = (Student) m.getObject();
 					System.out.println(s.toString());
 				}
			}
			
			//test for null user
			objectWriter.writeObject(new Message(0,new User()));
			objectWriter.flush();//send object to server
			
			System.out.println("object has sent to server");
			
			obj = objectReader.readObject();
			if(obj!=null){
 				Message m = (Message) obj;
 				if(m.getTaskCode()==-1){
 					System.out.println("something goes wrong");
 				}else{
 					Student s = (Student) m.getObject();
 					System.out.println(s.toString());
 				}
			}
			
			
			//test for unmatched userId and password
			objectWriter.writeObject(new Message(0,new User("jabigelow@ursinus.edu",null,"1561654564654",1)));
			objectWriter.flush();//send object to server
			
			System.out.println("object has sent to server");
			
			obj = objectReader.readObject();
			if(obj!=null){
 				Message m = (Message) obj;
 				if(m.getTaskCode()==-1){
 					System.out.println("something goes wrong");
 				}else{
 					Student s = (Student) m.getObject();
 					System.out.println(s.toString());
 				}
			}
			
			//shutdown the connection from the client
			objectWriter.writeObject(new Message(-2,null));
			objectWriter.flush();//send object to server
			
			objectReader.close();
			objectWriter.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
}

