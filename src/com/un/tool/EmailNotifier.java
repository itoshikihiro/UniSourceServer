/**   
 * @Title: EmailNotifier.java 
 * @Package com.un.tool 
 * @Description: TODO 
 * @author Jie Lin: kimihiro.lin80@gmail.com 
 * @date May 1, 2017 1:24:21 PM 
 * @version V1.0   
 */  
package com.un.tool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @ClassName: EmailNotifier 
 * @Description: TODO
 * @author Jie Lin: kimihiro.lin80@gmail.com
 * @date May 1, 2017 1:24:21 PM 
 *  
 */
public class EmailNotifier {
	private Socket connection;//a connection to smtp mail service

	private BufferedReader fromServer;
	private DataOutputStream toServer;

	private static final int SMTP_PORT = 25;
	private static final String CRLF = "\r\n";


	private static final String WEBMAILHOST = "webmail.ursinus.edu";

	private boolean isConnected = false;

	public EmailNotifier()
	{
		InetAddress DestAddr = null;
		try {
			DestAddr = InetAddress.getByName(WEBMAILHOST);
			connection = new Socket(DestAddr,SMTP_PORT);
			fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			toServer = new DataOutputStream(connection.getOutputStream());
			String reply = fromServer.readLine();
			if(parseReply(reply)!=220)
			{
				System.out.println("error in connecting");
				System.out.println(reply);
				return;
			}
			String localhost = (InetAddress.getLocalHost()).getHostName();
			try {
				sendCommand("HELO " + localhost, 250);
			} catch (IOException e) {
				System.out.println("Helo command fail. System aborts");
			}
			isConnected = true;
		} catch (Exception e) {
			System.out.println("error for initializing email notifier");
			return;
		}
	}
	
	public void send(String rcpt,String subject, String body) throws IOException
	{
		String headers = "From: <uniSource@uniSource.com>"+CRLF;
		headers+="To: "+rcpt+CRLF;
		headers+="Subject: "+subject+CRLF;
		SimpleDateFormat format = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss 'GMT'");
		String dateString = format.format(new Date());
		headers += "Date: " + dateString + CRLF;
		String sendData = headers + CRLF + body;
		sendCommand("MAIL FROM:<uniSource@uniSource.com>", 250);
		sendCommand("RCPT TO:<" + rcpt + ">", 250);
		sendCommand("DATA", 354);
		sendCommand(sendData + CRLF + ".", 250);
		
		close();
	}
	/*
	 * Send an SMTP command to the server. Check for reply code. Does not check
	 * for multiple reply codes (required for RCPT TO).
	 */
	private void sendCommand(String command, int replyCode) throws IOException {
		String reply = null;

		toServer.writeBytes(command + CRLF);
		reply = fromServer.readLine();
		if (parseReply(reply) != replyCode) {
			System.out.println("Error in command: " + command);
			System.out.println(reply);
			throw new IOException();
		}

	}
	// the command is for learning api
	/* Parse the reply line from the server. Returns the reply code. */
	private int parseReply(String reply) {
		int rev;
		String[] messageList = reply.split(" ");
		/*
		 * StringTokenizer parser = new StringTokenizer(reply); String replycode
		 * = parser.nextToken();
		 */
		// System.out.println(messageList[0]);
		// return (new Integer(replycode)).intValue();
		String replycode = messageList[0];
		rev = Integer.parseInt(replycode);

		return rev;
	}
	/*
	 * Close the connection. Try to send QUIT-commmand and then close the
	 * socket.
	 */
	public void close() {
		isConnected = false;
		try {
			sendCommand("QUIT", 221);
			connection.close();
		} catch (IOException e) {
			System.out.println("Unable to close connection: " + e);
			isConnected = true;
		}
	}
	/* Destructor. Closes the connection if something bad happens. */
	protected void finalize() throws Throwable {
		if (isConnected) {
			close();
		}
		super.finalize();
	}
}

