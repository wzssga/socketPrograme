package com.jiwang.http;

import java.io.ObjectInputStream.GetField;
import java.util.Date;

public class HttpStateCode {
	private String statecode;
	private String body;
	public HttpStateCode() {
		// TODO Auto-generated constructor stub
		this.statecode = "";
		this.body = "";
	}
	public HttpStateCode(String version, String code, String description) {
		this.statecode = "HTTP/" + version + " " + code + " " + description + "\r\n";
		Date now=new Date(); 
		this.statecode += "Date: "+ now + "\r\n";
		this.statecode += "Server: JHTTP 1.1\r\n";
		this.body = "";
	}
	public void setHeadAttributes(String title, String details) {
		this.statecode +=  title + ": " + details + "\r\n";
	}
	public String getStateCode() {
		return this.statecode +="\r\n";
	}
	public String getBody() {
		return this.body;
	}
	public void response404() {
		this.statecode += "HTTP/1.1 404 File Not Found\r\n";
		Date now=new Date();  
		this.statecode += "Date: "+ now + "\r\n";
		this.statecode += "Server: JHTTP 1.1\r\n";
		this.statecode += "Content-Type: text/html\r\n\r\n";
		this.body += "<HTML>\r\n" 
				   + "<HEAD><TITLE>File Not Found</TITLE></HRAD>\r\n" 
				   + "<BODY>\r\n"
				   + "<H1>HTTP Error 404: File Not Found</H1>"
				   + "</BODY></HTML>\r\n";
	}
	public void response400() {
		this.statecode += "HTTP/1.1 400 Bad Request\r\n";
		Date now=new Date();  
		this.statecode += "Date: "+ now + "\r\n";
		this.statecode += "Server: JHTTP 1.1\r\n";
		this.statecode += "Content-Type: text/html\r\n\r\n";
		this.body += "<HTML>\r\n" 
				   + "<HEAD><TITLE>Bad Request</TITLE></HRAD>\r\n" 
				   + "<BODY>\r\n"
				   + "<H1>HTTP Error 400: Bad Request</H1>"
				   + "</BODY></HTML>\r\n";
	}
	public void response414() {
		this.statecode += "HTTP/1.1 414 Request URI Too Long\r\n";
		Date now=new Date();  
		this.statecode += "Date: "+ now + "\r\n";
		this.statecode += "Server: JHTTP 1.1\r\n";
		this.statecode += "Content-Type: text/html\r\n\r\n";
		this.body += "<HTML>\r\n" 
				   + "<HEAD><TITLE>Request URI Too Long</TITLE></HRAD>\r\n" 
				   + "<BODY>\r\n"
				   + "<H1>HTTP Error 414: Request URI Too Long</H1>"
				   + "</BODY></HTML>\r\n";
	}
	public void response501() {
		this.statecode += "HTTP/1.1 501 Not Implemented\r\n";
		Date now=new Date();  
		this.statecode += "Date: "+ now + "\r\n";
		this.statecode += "Server: JHTTP 1.1\r\n";
		this.statecode += "Content-Type: text/html\r\n\r\n";
		this.body += "<HTML>\r\n" 
				   + "<HEAD><TITLE>Not Implemented</TITLE></HRAD>\r\n" 
				   + "<BODY>\r\n"
				   + "<H1>HTTP Error 501: Not Implemented</H1>"
				   + "</BODY></HTML>\r\n";
	}
	
	public void response505() {
		this.statecode += "HTTP/1.1 505 HTTP Version Not Supported\r\n";
		Date now=new Date();  
		this.statecode += "Date: "+ now + "\r\n";
		this.statecode += "Server: JHTTP 1.1\r\n";
		this.statecode += "Content-Type: text/html\r\n\r\n";
		this.body += "<HTML>\r\n" 
				   + "<HEAD><TITLE>HTTP Version Not Supported</TITLE></HRAD>\r\n" 
				   + "<BODY>\r\n"
				   + "<H1>HTTP Error 505: HTTP Version Not Supported</H1>"
				   + "</BODY></HTML>\r\n";
	}
	public void response403() {
		this.statecode += "HTTP/1.1 403 Forbidden\r\n";
		Date now=new Date();  
		this.statecode += "Date: "+ now + "\r\n";
		this.statecode += "Server: JHTTP 1.1\r\n";
		this.statecode += "Content-Type: text/html\r\n\r\n";
		this.body += "<HTML>\r\n" 
				   + "<HEAD><TITLE>Forbidden</TITLE></HRAD>\r\n" 
				   + "<BODY>\r\n"
				   + "<H1>HTTP Error 403: Forbidden</H1>"
				   + "</BODY></HTML>\r\n";
	}
	
}
