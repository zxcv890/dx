package com.MyNIO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dx on 2017/1/21.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //初始化
        NHttpClient httpClient = new NHttpClient();
        httpClient.init();
        //调用的url
        String url = "http://www.pconline.com.cn/";
        //调用的方法
        httpClient.getUrl(url, new NHttpClient.NHttpClientCallback() {
            public void finished(String content) {
                System.out.println("content=" + content.substring(0, 1000));
            }
        });
//注意这里是立即返回，可以根据需要进行处理
        System.in.read();
    }
    public static void testIO(String path, int i){
        InputStream inStream = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        OutputStream web = null;
        try {
            web = new FileOutputStream("d://test/" + i + ".html");
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            inStream = conn.getInputStream();
            bin = new BufferedInputStream(inStream);
            bout = new BufferedOutputStream(web);
            byte b[] = new byte[1024*1024];
            int len = bin.read(b);
            while (len > 0) {
                bout.write(b, 0, len);
                len = bin.read(b);
            }
            bout.flush();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }finally{
            try {
                if (bin != null)
                    bin.close();
                if (inStream != null)
                    inStream.close();
                if (bout != null)
                    bout.close();
                if (web != null)
                    web.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
