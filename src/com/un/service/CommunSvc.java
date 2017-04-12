    /**   
    * @Title: GetFromSvc.java 
    * @Package com.un.service 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 12, 2017 12:45:32 PM 
    * @version V1.0   
    */  
package com.un.service;

import com.un.pojo.Message;

/** 
    * @ClassName: GetFromSvc 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date Apr 12, 2017 12:45:32 PM 
    *  
    */
public interface CommunSvc {
	public void sendMessage(Message m);
	public Message getMessage(Message m);
}

