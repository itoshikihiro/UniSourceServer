    /**   
    * @Title: RegisterSvcImpl.java 
    * @Package com.un.service.impl 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date May 1, 2017 3:18:09 PM 
    * @version V1.0   
    */  
package com.un.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.un.pojo.User;
import com.un.service.RegisterSvc;
import com.un.tool.EmailNotifier;
import com.un.tool.OutPutAsFile;
import com.un.tool.UserListReader;

/** 
    * @ClassName: RegisterSvcImpl 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date May 1, 2017 3:18:09 PM 
    *  
    */
public class RegisterSvcImpl implements RegisterSvc {
	private EmailNotifier en = null;
	private UserListReader ulr = null;

	/** 
	* <p>Title: </p> 
	* <p>Description: </p>  
	*/
	public RegisterSvcImpl() {
		en = new EmailNotifier();
		ulr = new UserListReader();	
	}
	/* (non Javadoc) 
	* <p>Title: requestRegister</p> 
	* <p>Description: </p>  
	* @see com.un.service.RegisterSvc#requestRegister() 
	*/ 
	@Override
	public void requestRegister(String s) {
		//get an admin from admin list
		ArrayList <User> adminList = ulr.getAdminList();
		//a test method to see if it can get admin list
		//System.out.println(adminList);
		String rcpt = s;
		//System.out.println(rcpt);
		
		String subject = "A new register comes in";
		String body = "Dear Admin"+"\n\t"
				+ "You have a new register wants to register in this system. "
				+ "Please email "+rcpt+" to verify "
				+ "and go back to the system to confirm or disconfirm this registeration";
		
		Random rand = new Random();
		//randomely pick an admin from the list; 
		int  n = rand.nextInt(adminList.size());
		String adminID = adminList.get(n).getUserID();
		String data = "You have a new register from "+rcpt+". Do you want to him to get into this system";
		
		//Here we only need student's email address as the userId to confirm. The password will be given when the admin approve this registration
		//The rest information should be updated by student when they get into this system
		
		//write a todo list file for this admin
		OutPutAsFile.writerFile(adminID+"TDList", data);
		//send an email to notify him to check on this system
		try {
			en.send(rcpt, subject, body);
		} catch (IOException e) {
			System.out.println("Cannot send this email");
		}
	}

	/* (non Javadoc) 
	* <p>Title: confirmRegister</p> 
	* <p>Description: </p>  
	* @see com.un.service.RegisterSvc#confirmRegister() 
	*/ 
	@Override
	public void confirmRegister() {
		// TODO Auto-generated method stub
		
	}

}

