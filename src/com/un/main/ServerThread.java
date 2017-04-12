    /**   
    * @Title: ServerThread.java 
    * @Package com.un.main 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 11, 2017 12:49:20 AM 
    * @version V1.0   
    */  
package com.un.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** 
* @ClassName: ServerThread 
* @Description: Since this server is for multiple users to access at the same time, we need thread to handle each user's requests
* @author Jie Lin: kimihiro.lin80@gmail.com
* @date Apr 11, 2017 12:49:20 AM 
*  
*/
public class ServerThread extends Thread {
	private Socket client;
	//get message from client
	private BufferedReader bufferedReader;
	//send to client
	private PrintWriter printWriter;
	
	public ServerThread(Socket s) throws IOException{
		client = s;
		bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		printWriter = new PrintWriter(client.getOutputStream(), true);
		
		//print on server console about which client is getting the connection
		System.out.println("Client(" + getName() + ") come in...");
		
		start();
	}
	
	//the actual running method for multi thread
	@Override
	public void run() {
		try {
			String line = bufferedReader.readLine();
			//a test method
			System.out.println("Client(" + getName() + ") say: " + line);
			
			//a test method if client says bye, disconnect
			while (!line.equals("bye")) {
				printWriter.println("continue, Client(" + getName() + ")!");
				line = bufferedReader.readLine();
				System.out.println("Client(" + getName() + ") say: " + line);
			}
			printWriter.println("bye, Client(" + getName() + ")!");
			System.out.println("Client(" + getName() + ") exit!");
			printWriter.close();
			bufferedReader.close();
			client.close();
			
			
		} catch (IOException e) {
			System.out.println("Client(" + getName() + ") connects error"+e);
		}
	}
}

