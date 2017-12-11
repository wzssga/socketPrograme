package com.jiwang.test;

import java.io.*;
import java.net.*;



public class TestPut {
	public static String sendPut(String url, byte[] data) {
        String result = "";  
        BufferedReader br = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("PUT");
            // 发送PUT请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);           
            OutputStream raw = new BufferedOutputStream(conn.getOutputStream());          
            raw.write(data);
            raw.write("\r\n".getBytes());
//            raw.write("\r\n".getBytes());
            raw.flush();

            // 定义BufferedReader输入流来读取URL的响应
            DataInputStream in = new DataInputStream(conn.getInputStream());
            String line;           
            while( (line =in.readLine()) != null ){
                result += line;              
            }
            System.out.println(conn.getHeaderFields());
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("发送 put请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
        	try{
                if(br!=null){
                	br.close();
                }
                
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
	public static void main(String[] args) {
		File putFile = new File("C:\\Users\\10372\\Desktop\\test.txt");		
		try {
			
			
				DataInputStream fis=new DataInputStream(new BufferedInputStream(new FileInputStream(putFile)));
				byte[] theData=new byte[(int)putFile.length()];
				System.out.println(putFile.length());
				fis.readFully(theData);				
	            fis.close();
	            sendPut("http://127.0.0.1:8080/test1.txt",theData);

			
		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
