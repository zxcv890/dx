//package com.pay;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Formatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class WxWebSign {
//
//
//	public static String getWebPaySignString( String payid ){
//		String appId = WxConfiger.CorpID;
//		String timeStamp = create_timestamp();
//		String nonceStr = create_nonce_str();
//		String signType = "MD5";
//
//		String signStr = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=prepay_id=" + payid + "&signType=" + signType + "&timeStamp=" + timeStamp;
//		signStr += "&key=" + WxConfiger.PAYKEY;
//
//		String paySign = DigestUtils.md5Hex(signStr).toUpperCase();
//		String js = "timestamp:" + timeStamp +
//					",nonceStr:'" + nonceStr +
//					"',package:'prepay_id=" + payid +
//					"',signType:'MD5',paySign:'" + paySign + "',";
//		return js;
//	}
//
//	public static String getSignString( String url ){
//		Map<String, String> arr = sign( url );
//		String tt = "appId:'" + WxConfiger.CorpID + "',";
//		tt += "timestamp:" + arr.get("timestamp") + ",";
//		tt += "nonceStr: '" + arr.get("nonceStr") + "',";
//		tt += "signature: '" + arr.get("signature") + "',";
//
//		//log.info(tt);
//
//		return tt;
//	}
//
//	/**
//	 * 加密码串
//	 * @param jsapi_ticket
//	 * @param url
//	 * @return
//	 */
//    public static Map<String, String> sign(String url) {
//    	String jsapi_ticket = WxToken.getTick();
//        Map<String, String> ret = new HashMap<String, String>();
//        String nonce_str = create_nonce_str();
//        String timestamp = create_timestamp();
//        String string1;
//        String signature = "";
//
//        //注意这里参数名必须全部小写，且必须有序
//        string1 = "jsapi_ticket=" + jsapi_ticket +
//                  "&noncestr=" + nonce_str +
//                  "&timestamp=" + timestamp +
//                  "&url=" + url;
//        //log.info(string1);
//
//        try
//        {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(string1.getBytes("UTF-8"));
//            signature = byteToHex(crypt.digest());
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            e.printStackTrace();
//        }
//
//        ret.put("url", url);
//        ret.put("jsapi_ticket", jsapi_ticket);
//        ret.put("nonceStr", nonce_str);
//        ret.put("timestamp", timestamp);
//        ret.put("signature", signature);
//
//        return ret;
//    }
//
//    private static String byteToHex(final byte[] hash) {
//        Formatter formatter = new Formatter();
//        for (byte b : hash)
//        {
//            formatter.format("%02x", b);
//        }
//        String result = formatter.toString();
//        formatter.close();
//        return result;
//    }
//
//    private static String create_nonce_str() {
//        return UUID.randomUUID().toString();
//    }
//
//    private static String create_timestamp() {
//        return Long.toString(System.currentTimeMillis() / 1000);
//    }
//}
