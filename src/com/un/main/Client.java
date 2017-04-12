/**   
 * @Title: Client.java 
 * @Package com.un.main 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date Apr 11, 2017 3:13:18 PM 
 * @version V1.0   
 */  
package com.un.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
			socket.setSoTimeout(60000);

			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String result = "";
			while(result.indexOf("bye") == -1){
				BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
				printWriter.println(sysBuff.readLine());
				printWriter.flush();

				result = bufferedReader.readLine();
				System.out.println("Server say : " + result);
			}

			printWriter.close();
			bufferedReader.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
}

