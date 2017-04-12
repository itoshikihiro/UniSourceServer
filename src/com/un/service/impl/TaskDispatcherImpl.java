    /**   
    * @Title: TaskDispatcherImpl.java 
    * @Package com.un.service.impl 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date Apr 12, 2017 12:41:30 PM 
    * @version V1.0   
    */  
package com.un.service.impl;

import com.un.pojo.Message;
import com.un.service.CommunSvc;
import com.un.service.TaskDispatchSvc;

/** 
    * @ClassName: TaskDispatcherImpl 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date Apr 12, 2017 12:41:30 PM 
    *  
    */
public class TaskDispatcherImpl implements TaskDispatchSvc{

	/* (non Javadoc) 
	* <p>Title: dispath</p> 
	* <p>Description: use message to determine which service it goes to</p> 
	* @param m 
	* @see com.un.service.TaskDispatchSvc#dispath(com.un.pojo.Message) 
	*/ 
	@Override
	public void dispath(Message m) {
		if(!m.isFlag()){
		}
	}
	
}

