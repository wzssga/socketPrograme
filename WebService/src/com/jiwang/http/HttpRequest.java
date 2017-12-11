package com.jiwang.http;

import java.io.*;
import java.net.Socket;

public class HttpRequest {
	private String method;
	private String filename;
	private String version;
	private String Request;
	private StringBuffer Body = new StringBuffer();
	HttpRequest(){}
	public HttpRequest(Socket connection) {
		String rq = "";
		try {
			DataInputStream fis = new DataInputStream(connection.getInputStream());
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String request = "";
			int contentlength = 0;
			while((request = fis.readLine()) != null&&!request.isEmpty()){
					rq+=request+"\r\n";
				if (request.startsWith("Content-Length")){
					int begin=request.indexOf("accept-Lengh: ")+"accept-Length:  ".length();
					String postParamterLength=request.substring(begin).trim();
					contentlength=Integer.parseInt(postParamterLength);
//                    System.out.println("POST参数长度是："+Integer.parseInt(postParamterLength));
				}
			}	
			rq+="\r\n";
			String[] subStr = rq.split("\r\n");
			String[] subStr1 = subStr[0].split(" ");
			this.method = subStr1[0];
			this.filename = subStr1[1];		
			this.version = subStr1[2];
			StringBuffer sb=new StringBuffer();
			if(contentlength>0){
				for (int i = 0; i < contentlength; i++) {
					char a = (char)fis.read();
					sb.append(a);	
				}
				this.Body = sb;
				rq+=sb.toString()+"\r\n";
			}
		}catch (IOException e) {
			
		}
		this.Request = rq;
		System.out.println(rq);
	}
	
	public String getMethod() {
		return this.method;
	}
	public String getFileName() {
		return this.filename;
	}
	public String getVersion() {
		return this.version;
	}
	public String getRequest() {
		return this.Request;
	}
	public String getBody() {
		return this.Body.toString();
	}
}
