package com.jiwang.sierzi;

import java.io.*;
import java.net.*;

/**
 * Created by Rocky on 2017/12/7.
 */
public class TestDemo {

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(conn.getHeaderFields());
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static void main(String args[]) throws IOException{
        String result = sendPost("http://127.0.0.1:8080/","content");
        System.out.println(result);

    //        Socket s = new Socket();
    //        URL url = new URL("http:http://localhost/");
    //        String host = url.getHost();
    //        int port = url.getDefaultPort();
    //
    //        String path = " ";
    //        SocketAddress dest = new InetSocketAddress(host, port);
    //        s.connect(dest);
    //        OutputStreamWriter streamWriter = new OutputStreamWriter(s.getOutputStream());
    //        BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
    //        bufferedWriter.write("GET " + path + " HTTP/1.1\r\n");
    //        bufferedWriter.write("Host: " + host + "\r\n");
    //        bufferedWriter.write("\r\n");
    //        bufferedWriter.flush();
    }
}
