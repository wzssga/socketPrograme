package com.jiwang.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestDelete {
    public static String sendDelete(String url,int i) {
        String result = "";
        try {
            URL realUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            conn.setRequestMethod("DELETE");
            conn.setDoOutput(true);
            conn.setDoInput(true); 
            
            DataInputStream in = new DataInputStream(conn.getInputStream());
            String line = "";
            while( (line =in.readLine()) != null ){
                System.out.println(line);
                result += line;
            }
            System.out.println(conn.getHeaderFields());
            System.out.println(result);
            in.close();
        } catch(FileNotFoundException exception) {
        	System.out.println("文件不存在" + exception);
        }catch (Exception e) {
            System.out.println("发送DELETE请求出现异常" + e);
            e.printStackTrace();
        }
        finally{
        	
        }
        return result;
    }
    public static void main(String[] args) {
        sendDelete("http://127.0.0.1:8080/test1.txt",1);
    }
}
