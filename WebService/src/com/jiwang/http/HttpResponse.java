package com.jiwang.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;


public class HttpResponse {
	private Socket connection;
	private File documentRootDirectory;  
    private String indexFileName="baidu.html";
    
	public HttpResponse(File documentRootDirectory,String indexFileName,Socket connection) {
		if (documentRootDirectory.isFile()) {  
            throw new IllegalArgumentException();  
        }  
        this.documentRootDirectory=documentRootDirectory;  
        try {  
            this.documentRootDirectory=documentRootDirectory.getCanonicalFile();  
        } catch (IOException e) {  
        }  
          
        if (indexFileName!=null) {  
            this.indexFileName=indexFileName;  
        }
        this.connection=connection; 
	}
	
	
	public void handleRequest(HttpRequest httpRequest) {
		String filename = httpRequest.getFileName();
		String version = httpRequest.getVersion();
		if(filename.length() > 255) {
			handleLongRequest(httpRequest);
		}else if(isBad(filename)) {
			handleBadRequest(httpRequest);
		}else if(!version.equals("HTTP/1.1")) {
			handleErrHttpVersion(httpRequest);
		}else {
			String method = httpRequest.getMethod();
			switch (method) {
			case "GET":
				handleGET(httpRequest);
				break;
			case "PUT":
				handlePUT(httpRequest);
				break;
			case "POST":
				handlePOST(httpRequest);
				break;
			case "DELETE":
				handleDELETE(httpRequest);
				break;
			default:
				handleUnavailable(httpRequest);
				break;
			}
		}
		
	}
	
	public boolean isBad(String url) {
		boolean result = false;
		if(url.contains("%") || url.contains("#") || url.contains("&") || url.contains("?") || url.contains("\"")
		|| url.contains("*") || url.contains("|") || url.contains("\\") || url.contains(":") || url.contains(">")
		|| url.contains("<")) {
			result = true;
		}
		return result;
	}
	public void handleLongRequest(HttpRequest httpRequest) {
		try {
			OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
			Writer out=new OutputStreamWriter(raw);
			String version = httpRequest.getVersion();
			if (version.startsWith("HTTP")) {  
            	HttpStateCode stateCode = new HttpStateCode();
            	stateCode.response414();
            	String outStatecode = stateCode.getStateCode();
            	String outBody = stateCode.getBody();
            	out.write(outStatecode); 
                out.flush();
                out.write(outBody);
            }  
			out.close();
			raw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleBadRequest(HttpRequest httpRequest) {
		try {
			OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
			Writer out=new OutputStreamWriter(raw);
			String version = httpRequest.getVersion();
			if (version.startsWith("HTTP")) {  
            	HttpStateCode stateCode = new HttpStateCode();
            	stateCode.response400();
            	String outStatecode = stateCode.getStateCode();
            	String outBody = stateCode.getBody();
            	out.write(outStatecode); 
                out.flush();
                out.write(outBody);
            }  
			out.close();
			raw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleErrHttpVersion(HttpRequest httpRequest) {
		try {
			OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
			Writer out=new OutputStreamWriter(raw);
			String version = httpRequest.getVersion();
			if (version.startsWith("HTTP")) {  
            	HttpStateCode stateCode = new HttpStateCode();
            	stateCode.response505();
            	String outStatecode = stateCode.getStateCode();
            	String outBody = stateCode.getBody();
            	out.write(outStatecode); 
                out.flush();
                out.write(outBody);
            }  
			out.close();
			raw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleUnavailable(HttpRequest httpRequest) {
		try {
			OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
			Writer out=new OutputStreamWriter(raw);
			String version = httpRequest.getVersion();
			if (version.startsWith("HTTP")) {  
            	HttpStateCode stateCode = new HttpStateCode();
            	stateCode.response501();
            	String outStatecode = stateCode.getStateCode();
            	String outBody = stateCode.getBody();
            	out.write(outStatecode); 
                out.flush();
                out.write(outBody);
            }  
			out.close();
			raw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handlePOST(HttpRequest httpRequest){
		try {
//			OutputStream out = connection.getOutputStream();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\baidu.html"));
			HttpStateCode stateCode = new HttpStateCode("1.1", "200", "OK");
			stateCode.setHeadAttributes("Content-Type", "text/html");
			String outStr = stateCode.getStateCode();
            out.write(outStr.getBytes());  
            
			String line = null;
			while (true) {
				if ((line = br.readLine()) != null) {
					out.write(line.getBytes());
				} else
					break;
			}
			out.flush();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}finally{
			try {
				connection.close();
			} catch (IOException e2) {
			}

		}
		System.out.println("传输完成");
	}
	
	public void handlePUT(HttpRequest httpRequest) {
		String fileName = httpRequest.getFileName();
		File rqFile=new File(documentRootDirectory,fileName.substring(1,fileName.length())); 
		
		try {
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			//存储文件
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(  
					rqFile));
			dos.writeBytes(httpRequest.getBody().toString());
			dos.flush();  
            dos.close();
            //返回状态码
			if(!rqFile.exists()) {
				HttpStateCode stateCode = new HttpStateCode("1.1", "201", "Created");
				stateCode.setHeadAttributes("Content-Type", "text/html");
				String outStr = stateCode.getStateCode();
                out.write(outStr.getBytes());  
                out.flush();  
			}else {
				HttpStateCode stateCode = new HttpStateCode("1.1", "200", "OK");
				stateCode.setHeadAttributes("Content-Type", "text/html");
				String outStr = stateCode.getStateCode();
                out.write(outStr.getBytes());  
                out.flush();  
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (IOException e2) {
			}

		}
	}
	
	public void handleGET(HttpRequest httpRequest) {
		try {
			OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String root = documentRootDirectory.getPath();
			
			String fileName = httpRequest.getFileName();
	        if (fileName.endsWith("/")) {  
	            fileName+=indexFileName;  
	        }
	        String contentType = guessContentTypeFromName(fileName);  
	        String version = httpRequest.getVersion();
	        File theFile=new File(documentRootDirectory,fileName.substring(1,fileName.length())); 
	        if (theFile.canRead()&&theFile.getCanonicalPath().startsWith(root)) {  
	            DataInputStream fis=new DataInputStream(new BufferedInputStream(new FileInputStream(theFile)));  
	            byte[] theData=new byte[(int)theFile.length()];  
	            fis.readFully(theData);  
	            fis.close();  
	            if (version.startsWith("HTTP")) { 
	            	HttpStateCode stateCode = new HttpStateCode("1.1", "200", "OK");
	            	stateCode.setHeadAttributes("Content-length",Integer.toString(theData.length));
	            	stateCode.setHeadAttributes("Content-Type",contentType);
	            	String outStr = stateCode.getStateCode();
	            	out.write(outStr.getBytes()); 
	                out.flush();  
	            }  
	            raw.write(theData);  
	            raw.flush();  
	        }else {  
	            if (version.startsWith("HTTP")) {  
	            	HttpStateCode stateCode = new HttpStateCode();
	            	stateCode.response404();
	            	String outStatecode = stateCode.getStateCode();
	            	String outBody = stateCode.getBody();
	            	out.write(outStatecode.getBytes()); 
	                out.flush();
	                out.write(outBody.getBytes());
	            }  
	        }
	        out.close();
	        raw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}finally{  
            try {  
                connection.close();  
            } catch (IOException e2) {  
            }  
              
        }   
	}    
	
	public void handleDELETE(HttpRequest httpRequest){
		try{
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String fileName = httpRequest.getFileName();
			String version = httpRequest.getVersion();
			String root = documentRootDirectory.getPath();
			fileName = root + fileName;
			System.out.println(fileName);
			File file = new File(fileName);			
			if (!file.exists()) {				
				System.out.println("删除文件失败:" + fileName + "不存在！");				
				HttpStateCode stateCode = new HttpStateCode();
	            stateCode.response404();
	            String outStatecode = stateCode.getStateCode();
	            String outBody = stateCode.getBody();
	            out.write(outStatecode.getBytes()); 
	            out.flush();
	            out.write(outBody.getBytes());		
	            System.out.println("11111111111111");
			}else{
				System.out.println("11111111111111111");
				boolean canExecute = file.canExecute();
				if(canExecute){
					file.delete();
					System.out.println("删除成功");
					HttpStateCode stateCode = new HttpStateCode("1.1", "200", "OK");
					String outStr = stateCode.getStateCode();
	            	out.write(outStr.getBytes()); 
					out.flush();
				}else{
					HttpStateCode stateCode = new HttpStateCode();
	            	stateCode.response403();
	            	String outStatecode = stateCode.getStateCode();
	            	String outBody = stateCode.getBody();
	            	out.write(outStatecode.getBytes()); 
	                out.flush();
	                out.write(outBody.getBytes());	
				}
			}
			out.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
		}finally{
			try {
				connection.close();
			} catch (IOException e2) {
			}
		}
	}

	
	public String guessContentTypeFromName(String name) {  
		if (name.endsWith(".html")||name.endsWith(".htm") || name.endsWith(".txt")||name.endsWith(".aspx")) {  
            return "text/html";  
        }else if (name.endsWith(".java")) {  
            return "text/plain";  
        }else if (name.endsWith(".gif")) {  
            return "image/gif";  
        }else if (name.endsWith(".png")) {  
            return "image/png";  
        }else if (name.endsWith(".class")) {  
            return "application/octet-stream";  
        }else if (name.endsWith(".jpg")||name.endsWith(".jpeg")) {  
            return "image/jpeg";  
        }else if (name.endsWith(".css")) {  
            return "text/css";  
        }else if (name.endsWith(".js")) {  
            return "application/x-javascript";  
        }else {  
            return "text/plain";  
        }  
    }  
}
