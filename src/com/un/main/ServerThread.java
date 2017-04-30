    /**   
    * @Title: ServerThread.java 
    * @Package com.un.main 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 11, 2017 12:49:20 AM 
    * @version V1.0   
    */  
package com.un.main;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.un.pojo.Message;
import com.un.service.TaskDispatchSvc;
import com.un.service.impl.TaskDispatcherImpl;

/** 
* @ClassName: ServerThread 
* @Description: Since this server is for multiple users to access at the same time, we need thread to handle each user's requests
* @author Jie Lin: kimihiro.lin80@gmail.com
* @date Apr 11, 2017 12:49:20 AM 
*  
*/
public class ServerThread extends Thread {
	private Socket client=null;
	/*//get message from client
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;*/
	//get object from client
	private ObjectInputStream objectReader=null;
	//send object to client
	private ObjectOutputStream objectWriter=null;
	private TaskDispatchSvc TDI=null;
	
	
	public ServerThread(Socket s){
		client = s;
		//initializing all private objects
		/*bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		printWriter = new PrintWriter(client.getOutputStream(), true);*/
		try{
			//client.setSoTimeout(60000);
			//the sequence of initialization does matter
			objectWriter = new ObjectOutputStream(client.getOutputStream());
			objectReader = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
		}catch(Exception e){
			System.out.println("Initialization error");
		}
		//initialize the task dispatcher interface
		TDI = new TaskDispatcherImpl(client,objectWriter);
		
		//print on server console about which client is getting the connection
		System.out.println("Client(" + getName() + ") come in...");
		
		start();
	}
	
	//the actual running method for multi thread
	@Override
	public void run() {
		try {
			//read object from client
			Object obj = objectReader.readObject();
			Message mes = (Message) obj;//convert object to Message class
			while(mes.getTaskCode()!=-2)//while the task code is not equal to -2, it will continue to dispatch
			{
				TDI.dispatch(mes);
				obj = objectReader.readObject();
				mes = (Message) obj;
			}
			objectReader.close();
			objectWriter.close();
			client.close();
			System.out.println("Client(" + getName() + ") disconnected...");
		} catch (Exception e) {
			System.out.println("Client(" + getName() + ")'s connection lost");
		}
	}
}

