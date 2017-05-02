    /**   
    * @Title: PassGenerator.java 
    * @Package com.un.tool 
    * @Description: TODO 
    * @author Jie Lin: kimihiro.lin80@gmail.com 
    * @date May 1, 2017 10:52:48 PM 
    * @version V1.0   
    */  
package com.un.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** 
    * @ClassName: PassGenerator 
    * @Description: TODO
    * @author Jie Lin: kimihiro.lin80@gmail.com
    * @date May 1, 2017 10:52:48 PM 
    *  
    */
public class PassGenerator {

	/**
	 * generate a random password
	 * 生成随机密码生成方式二
	 * 密码字典 -> 随机打乱集合顺序 -> 随机获取字符
	 * @param len 生成密码长度
	 * @return
	 */
	public static String getPassWordTwo(int len){
	    int i;  //生成的随机数
	    int count = 0; //生成的密码的长度
	    // 密码字典
	    char[] str = {
	            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
	            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	            '~', '!', '@', '#', '$', '%', '^', '-', '+'
	    };
	    StringBuffer stringBuffer = new StringBuffer("");
	    List<String> list = new ArrayList<String>();
	    Random r = new Random();
	    for (i = 0; i<str.length; i++) {
	        list.add(str[i] + "");
	    }
	    Collections.shuffle(list);
	    while(count < len){
	        //生成 0 ~ 密码字典-1之间的随机数
	        i = r.nextInt(list.size());
	        stringBuffer.append(list.get(i));
	        count ++;
	    }
	    return stringBuffer.toString();
	}
	
}

