    /**   
    * @Title: TaskDispatcher.java 
    * @Package com.un.service 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 12, 2017 12:28:13 PM 
    * @version V1.0   
    */  
package com.un.service;

import java.io.IOException;

import com.un.pojo.Message;

/** 
    * @ClassName: TaskDispatcher 
    * @Description: use message class's task code to dispatche whic sever
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date Apr 12, 2017 12:28:13 PM 
    *  
    */
public interface TaskDispatchSvc {
	public void dispatch(Message m);
	public void returnErrorMes();
	public void loginCheck(Message m) throws IOException;
	public void returnMes(Message m) throws IOException;
	public void requestRegister(Message m);
}

