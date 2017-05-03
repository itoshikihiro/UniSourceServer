/**   
 * @Title: FileWriter.java 
 * @Package com.un.tool 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date May 1, 2017 5:48:16 PM 
 * @version V1.0   
 */  
package com.un.tool;

import java.io.FileWriter;
import java.io.IOException;

/** 
 * @ClassName: FileWriter 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date May 1, 2017 5:48:16 PM 
 *  
 */
public class OutPutAsFile {
	final public static String FILEADD = "./Content/files/";
	final public static String SUFFIX = ".csv";


	public static void writerFile(String fileName, String data)
	{
		try
		{
			//use this constructor means if there exists such file
			//directly add more String at the end of this file
			FileWriter writer=new FileWriter(FILEADD+fileName+SUFFIX,true);
			writer.write(data+"\n");
			writer.close();
		}
		catch(IOException e)//if it catches any exception
		{
			System.out.println("Can not output as a file");
		}
	}

}

