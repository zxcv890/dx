package com.MyNIO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws Exception {
        //初始化
        NHttpClient httpClient = new NHttpClient();
        httpClient.init();
        //调用的url
        String url = "http://news.baidu.com/";
        //调用的方法
        httpClient.getUrl(url, new NHttpClient.NHttpClientCallback() {
            public void finished(String content) {
                System.out.println("content=" + content);
            }
        });

        //这里测试多次连接
//        String[] urlpaths = {"http://www.people.com.cn/",
//                "http://www.xinhuanet.com/","http://www.cctv.com/",
//                "http://www.qq.com/","http://www.163.com/",
////                "https://www.baidu.com/index.php?tn=78040160_50_pg",
////                "http://www.suning.com/?utm_source=qqdh&utm_medium=mingzhan&utm_campaign=daohang",
////                "http://bj.jumei.com/?referer=qq_nav&utm_content=mz&utm_medium=nav&utm_source=qq",
////                "http://toutiao.sogou.com/?fr=qqxwtt",
//                "https://www.sogou.com/"};
//        for (String urlpath : urlpaths){
//            httpClient.getUrl(urlpath, new NHttpClient.NHttpClientCallback() {
//                public void finished(String content) {
//                    System.out.println("content=" + content);
//
//                }
//            });
//        }
        //注意这里是立即返回，可以根据需要进行处理
//        System.in.read();
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
