//package com.dx.test;
//
//import java.io.*;
//import java.net.URL;
//import java.nio.CharBuffer;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;
//import java.util.zip.ZipOutputStream;
//
///**
// * 向d://test.xls中追加内容，（前提是：必须已经存在字段列头，可以没有内容）
// *
// * 这个是一个案例，根据案例再发挥
// * @author leiwei 2012-02-09
// *
// */
//public class TestExceL{
//	final static String urlLogin="http://203.110.174.169:7011/rs/services";
//	final static String urlServic="http://203.110.174.169:7014/services";
//	public static void main(String[] args) {
//		List<Record> list = new ArrayList<Record>();
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		String getPageNumber = getPara("getPageNumber");
//		String registerNo = getPara("registerNo");
//		try {
//			String username = "xzy121";
//			String userPassword = "a11111111";
//			username = DES.encrypt(username);
//			userPassword = DES.encrypt(userPassword);
//			java.net.URL login = null;//登录URL
//			java.net.URL sel = null;//查询URL  唯一
//			java.net.URL logout=null;//退出URL
//			//登录
//			Record record = Db.findFirst("select ZDUSERNAME, ZDPWD from s_users where id = ?", ji.UserId);
//			login = new URL( urlLogin+"/LoginService");
//			LoginEndpoint service = new LoginEndpointServiceLocator().getLoginService(login);
//			byte[] loginToken = service.login(DES.encrypt(record.get("ZDUSERNAME").toString()).getBytes(),DES.encrypt(record.get("ZDPWD").toString()).getBytes());
//			String strloginToken = new String(loginToken);
//			sel = new URL( urlServic + "/QueryIdService");
//			QueryByIdEndpoint sel2Id=new QueryByIdEndpointServiceLocator().getQueryIdService(sel);
//			byte[] info=sel2Id.enquery(loginToken, DES.encrypt(registerNo).getBytes());
//			//解压 >>>>>>>>>>>>>>>>
//			String xmlString=unZip(info);
////			System.out.println("解压="+xmlString);
//			FileWriter fw = new FileWriter("d:/XML.XML");
//			fw.write(xmlString);
//			fw.close();
//			//解析xml
//			InputStream inputStream = new FileInputStream("d:/XML.XML");
//			SAXReader reader = new SAXReader();
//			Document document = reader.read(inputStream);
//			Element root = document.getRootElement();
//			Iterator<Element> iterator = root.elements().iterator();
//			while(iterator.hasNext()){
//				Element element = iterator.next();
//				Iterator<Element> iterator2 = element.elements().iterator();
////				System.out.println(element.getName());
//				if(element.isTextOnly()){
//					map.put(element.getName(), element.getStringValue());
//				}
//				if(element.getName().equals("data")){
//					while(iterator2.hasNext()){
//						Element element2 = iterator2.next();
//						Iterator<Element> iterator3 = element2.elements().iterator();
////						System.out.println(element2.getName());
//						if(element2.getName().equals("results")){
//							while(iterator3.hasNext()){
//								Element element3 = iterator3.next();
//								Iterator<Element> iterator4 = element3.elements().iterator();
////								System.out.println(element3.getName());
//								if(element3.getName().equals("result")){
//									Record record = new Record();
//									while(iterator4.hasNext()){
//										Element element4 = iterator4.next();
////										System.out.println(element4.getName());
//										record.set(element4.getName(), element4.getStringValue());
//									}
//									list.add(record);
//								}
//							}
//						}
//					}
//				}
//			}
//			map.put("list", list);
//			setAttr("myPage", Universal_Method_ZRZ.test2(pageNumber, 5, list.size()));
//			setAttr("map", map);
//			renderJsp("searchResult.jsp");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	//压缩
//	public static byte[] zip(byte[] data) {
//		byte[] b = null;
//		try {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			ZipOutputStream zip = new ZipOutputStream(bos);
//			ZipEntry entry = new ZipEntry("zip");
//			entry.setSize(data.length);
//			zip.putNextEntry(entry);
//			zip.write(data);
//			zip.closeEntry();
//			zip.close();
//			b = bos.toByteArray();
//			bos.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return b;
//	}
//	//解压
//	public static String unZip(byte[] data) {
//		byte[] b = null;
//		try {
//			ByteArrayInputStream bis = new ByteArrayInputStream(data);
//			ZipInputStream zip = new ZipInputStream(bis);
//			while (zip.getNextEntry() != null) {
//				byte[] buf = new byte[1024];
//				int num = -1;
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				while ((num = zip.read(buf, 0, buf.length)) != -1) {
//					baos.write(buf, 0, num);
//				}
//				b = baos.toByteArray();
//				baos.flush();
//				baos.close();
//			}
//			zip.close();
//			bis.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return new String(b);
//	}
//	//读XML
//	static void testXML(byte[] bs) {
//		try {
//			String aa = "";
//			if (bs[0] == -17 && bs[1] == -69 && bs[2] == -65) {
//				byte[] nbs = new byte[bs.length - 3];
//				System.arraycopy(bs, 3, nbs, 0, nbs.length);
//
//				aa = new String(nbs);
//				System.out.println(aa);
//			}
//
//			org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(aa);
//
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	//字符数据转字符串
//	public static String bytesToHexString(byte[] bArray) {
//		StringBuffer sb = new StringBuffer(bArray.length);
//		String sTemp;
//		for (int i = 0; i < bArray.length; i++) {
//			sTemp = Integer.toHexString(0xFF & bArray[i]);
//			if (sTemp.length() < 2)
//				sb.append(0);
//			sb.append(sTemp.toUpperCase());
//		}
//		return sb.toString();
//	}
//}
