/**   
 * @Title: UnServer.java 
 * @Package com.un.main 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date Apr 11, 2017 12:32:58 AM 
 * @version V1.0   
 */  
package com.un.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/** 
 * @ClassName: UnServer 
 * @Description: this is the main method that server side needs to run
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date Apr 11, 2017 12:32:58 AM 
 *  
 */
public class UnServer extends ServerSocket {

	//the port number for binding service
	private static final int SERVER_PORT = 6666;

	public UnServer() throws IOException{
		super(SERVER_PORT);

		try{
			while(true){
				//accept the connection, which will use ServerSocket class's method to bind connection
				Socket socket = accept();
				//if there exists a client connection, open a thread
				new ServerThread(socket);
			}
		}catch(IOException e){
			System.out.println("connection error: "+e);
		}finally{
			close();//close the connection. Throwing exception here. 
		}
	}


}

