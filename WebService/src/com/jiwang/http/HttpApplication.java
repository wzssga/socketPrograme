package com.jiwang.http;


import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.DataInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.OutputStreamWriter;  
import java.io.Reader;  
import java.io.Writer;  
import java.net.Socket;  
import java.util.Date;  
import java.util.List;  
import java.util.LinkedList;  
import java.util.StringTokenizer;

import javax.xml.transform.OutputKeys; 

public class HttpApplication implements Runnable {
	private static List pool=new LinkedList();  
    private File documentRootDirectory;  
    private String indexFileName="index.html";  
      
    public HttpApplication(File documentRootDirectory,String indexFileName) {  
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
    }  
      
    public static void processRequest(Socket request) {  
        synchronized (pool) {  
            pool.add(pool.size(),request);  
            pool.notifyAll();  
        }  
    }  
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub        
        while (true) {  
            Socket connection;  
            synchronized (pool) {  
                while (pool.isEmpty()) {  
                    try {  
                        pool.wait();  
                    } catch (InterruptedException e) {  
                    }  
                      
                }  
                connection=(Socket)pool.remove(0);  
            }  
              
            try {
                HttpRequest httpRequest = new HttpRequest(connection);               
                HttpResponse httpResponse = new HttpResponse(documentRootDirectory, indexFileName, connection);
                httpResponse.handleRequest(httpRequest);
            }finally{  
                try {  
                    connection.close();  
                } catch (IOException e2) {  
                }  
                  
            }  
        } 
	}
}
