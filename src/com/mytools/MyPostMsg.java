package com.mytools;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;

class JmwywPostMsg {
	//public static String ServerPath = "";
	
	/*
	public static void PostTextJSONMsg( String openid, String content ){
	   String msg = "{\"touser\":\"" + openid + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + content + "\"}}";
	   JmwywController.PostJSONMsg( "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getToken(), msg );
	}
	*/

	
	/**
	 * HTTP请求
	 * 
	 * @param params
	 * @return
	 */
	public static String HttpParams(String action, Map<String, String> params) {
		return submitPostData( action, params, "UTF-8", "POST");
	}

	public static String HttpGetParams(String action, Map<String, String> params) {
		return submitPostData( action, params, "UTF-8", "GET");
	}

	private static String submitPostData(String strUrlPath,
			Map<String, String> params, String encode, String Method) {
		byte[] data = getRequestData(params, encode).toString().getBytes();//         
		try {

			// String urlPath = "http://192.168.1.9:80/JJKSms/RecSms.php";
			URL url = new URL(strUrlPath);

			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setConnectTimeout(10000); //
			httpURLConnection.setDoInput(true); //
			if (Method.equals("GET")) {
				httpURLConnection.setDoOutput(false); //
			} else {
				httpURLConnection.setDoOutput(true); //
			}
			httpURLConnection.setRequestMethod(Method); // Post
			httpURLConnection.setUseCaches(false); // ʹ Post
			//
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			//
			httpURLConnection.setRequestProperty("Content-Length", String
					.valueOf(data.length));
			//

			if (Method.equals("GET")) {

			} else {
				OutputStream outputStream = httpURLConnection.getOutputStream();
				outputStream.write(data);
				outputStream.flush();
				outputStream.close();
			}

			int response = httpURLConnection.getResponseCode(); //
			if (response == HttpURLConnection.HTTP_OK) {
				InputStream inptStream = httpURLConnection.getInputStream();
				return dealResponseResult(inptStream); //
			}
		} catch (SocketTimeoutException e1) {
			return "Error";
		} catch (IOException e) {
			return "Error";
		} catch (Exception e) {
			return "Error";
		} finally {

		}
		return "Error";
	}

	/*
	 * Function : ��װ��������Ϣ Param : params���������ݣ�encode�����ʽ
	 */
	public static StringBuffer getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // �洢��װ�õ���������Ϣ
		if (params.size() == 0)
			return stringBuffer;
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=").append(
						URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // ɾ������һ��"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/*
	 * Function : Ӧ ת ַ Param : inputStream Ӧ
	 */
	private static String dealResponseResult(InputStream inputStream) {
		String resultData = null; //
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			resultData = new String(byteArrayOutputStream.toByteArray(),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("编码异常" + e.getMessage());
		}
		return resultData;
	}

	public static String PostSSLJSONMsg( String _url, String msg ){
		String ri = "";
        KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		}
        FileInputStream instream = null;
		try {
			instream = new FileInputStream(new File(""));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        try {
            try {
				keyStore.load(instream, "".toCharArray());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } finally {
            try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "".toCharArray()).build();
		} catch (KeyManagementException e1) {
		} catch (UnrecoverableKeyException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		}
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
        	org.apache.http.client.methods.HttpPost post = new org.apache.http.client.methods.HttpPost(_url);
        	
        	StringEntity myEntity = new StringEntity(msg, "UTF-8");
        	//List<org.apache.http.NameValuePair> params=new ArrayList<org.apache.http.NameValuePair>();  
            //建立一个NameValuePair数组，用于存储欲传送的参数  
            //params.add(new BasicNameValuePair("xmldate", msg));  
            //添加参数  
            post.setEntity(myEntity);  
                     
            //设置编码  
            HttpResponse response = httpclient.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();


            if(statusCode != HttpStatus.SC_OK){
                //logger.error("Method failed:"+response.getStatusLine());
                //status = 1;
            }
             
                //Read the response body
            ri = EntityUtils.toString(response.getEntity(), "UTF-8");
        	//System.out.println( body );
            //HttpGet httpget = new HttpGet("https://api.mch.weixin.qq.com/secapi/pay/refund");

            /*
            System.out.println("executing request" + post.getRequestLine() );

            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        System.out.println(text);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
            */
        } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return ri;
	}
	
	public static String PostJSONMsg(String _url, String msg) {
		String ri = "Error";
		try {
			// 创建连接
			URL url = new URL(_url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.connect();

			// POST请求
			DataOutputStream out = new DataOutputStream(connection
					.getOutputStream());
			/*
			 * JSONObject obj = new JSONObject(); obj.element("app_name",
			 * "asdf"); obj.element("app_ip", "10.21.243.234");
			 * obj.element("app_port", 8080); obj.element("app_type", "001");
			 * obj.element("app_area", "asd");
			 */
			out.write(msg.getBytes("utf-8"));
			// out.writeBytes( msg );
			out.flush();
			out.close();

			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb);

			ri = sb.toString();

			reader.close();
			// 断开连接
			connection.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ri;
	}
}
