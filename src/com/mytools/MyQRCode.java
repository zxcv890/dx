package com.mytools;

import com.swetake.util.Qrcode;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;  
import java.io.File;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  

import javax.imageio.ImageIO;  
  
public class MyQRCode {
	
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgPath 图片路径 
     */  
    public void encoderQRCode(String content, String imgPath) {  
        this.encoderQRCode(content, imgPath, "png", 7);  
    }  
      
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     */  
    public void encoderQRCode(String content, OutputStream output) {  
        this.encoderQRCode(content, output, "png", 7);  
    }  
      
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgPath 图片路径 
     * @param imgType 图片类型 
     */  
    public void encoderQRCode(String content, String imgPath, String imgType) {  
        this.encoderQRCode(content, imgPath, imgType, 7);  
    }  
      
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     * @param imgType 图片类型 
     */  
    public void encoderQRCode(String content, OutputStream output, String imgType) {  
        this.encoderQRCode(content, output, imgType, 7);  
    }  
    
//    public static void main(String[] args) {
//    	try {
//			BufferedImage image = ImageIO.read();
//			
//			ImageIO.write( image, "jpeg", new File ( "C:/test.jpg" ));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
    
    /**
     * 在一张图上绘制二维码
     */
    public void encodeQRCodeAndImage( String path, String content, OutputStream output, String imgType, int size ){
        try {
        	System.out.println( path );
        	// 生成二维码QRCode图片  
        	BufferedImage image = ImageIO.read( new File( path ) );

        	Graphics2D g = image.createGraphics();
        	
        	BufferedImage barcodeImg = this.qRCodeCommon(content, imgType, size); 
        	g.drawImage(barcodeImg, 430, 870, 200, 200, null);
        	
        	g.dispose();
        	
			ImageIO.write(image, imgType, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
  
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgPath 图片路径 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     */  
    public void encoderQRCode(String content, String imgPath, String imgType, int size) {  
        try {  
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);  
              
            File imgFile = new File(imgPath);  
            // 生成二维码QRCode图片  
            ImageIO.write(bufImg, imgType, imgFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     */  
    public void encoderTextQRCode(String text, String content, OutputStream output, String imgType, int size) {  
        try {  
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);
            Graphics2D gs = bufImg.createGraphics();
    		//Font font=new Font("Serif", Font.PLAIN, 80);
    		Color co=new Color(0,0,0);
    		gs.setColor(co);
            gs.drawString(text, 0, 0);
            gs.dispose();
            // 生成二维码QRCode图片  
            ImageIO.write(bufImg, imgType, output);
            bufImg.flush();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }    
    
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     */  
    public void encoderQRCode(String content, OutputStream output, String imgType, int size) {  
        try {  
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size); 
            
            // 生成二维码QRCode图片  
            ImageIO.write(bufImg, imgType, output);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 生成二维码(QRCode)图片的公共方法 
     * @param content 存储内容 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     * @return 
     */  
    private BufferedImage qRCodeCommon(String content, String imgType, int size) {  
        BufferedImage bufImg = null;  
        try {  
            Qrcode qrcodeHandler = new Qrcode();
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，
            //排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
            qrcodeHandler.setQrcodeErrorCorrect('H');
            qrcodeHandler.setQrcodeEncodeMode('B');
            
            // 获得内容的字节数组，设置编码格式
            byte[] contentBytes = content.getBytes("utf-8");
            int mySize = 0;
            boolean[][] codeOut = null;
            int imgSize = 0;//67 + 49 * (mySize - 1);
            int itmeSize = size;
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
        		codeOut = qrcodeHandler.calQrcode(contentBytes);
                mySize = qrcodeHandler.getQrcodeVersion();
                imgSize = 20 + codeOut.length * itmeSize;
                //System.out.println( codeOut.length );
            }
            
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            //qrcodeHandler.setQrcodeVersion(size);  
  
            // 图片尺寸  
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            // 设置背景颜色  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, imgSize, imgSize);  
  
            // 设定图像颜色> BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量，不设置可能导致解析出错  
            int pixoff = 10;
            
            // 输出内容> 二维码  
            //System.out.println( "级别：" + mySize );
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
                //boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * itmeSize + pixoff, i * itmeSize + pixoff, itmeSize, itmeSize);  
                        }
                    }  
                }  
            } else {  
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");  
            }  
            gs.dispose();  
            bufImg.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bufImg;  
    }  
      
    /** 
     * 解析二维码（QRCode） 
     * @param imgPath 图片路径 
     * @return 
     */  
    public String decoderQRCode(String imgPath) {  
        // QRCode 二维码图片的文件  
        File imageFile = new File(imgPath);  
        BufferedImage bufImg = null;  
        String content = null;  
        try {  
            bufImg = ImageIO.read(imageFile);  
            QRCodeDecoder decoder = new QRCodeDecoder();  
            content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");   
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {
            System.out.println("Error: " + dfe.getMessage());  
            dfe.printStackTrace();  
        }  
        return content;  
    }  
      
    /** 
     * 解析二维码（QRCode） 
     * @param input 输入流 
     * @return 
     */  
    public String decoderQRCode(InputStream input) {  
        BufferedImage bufImg = null;  
        String content = null;  
        try {  
            bufImg = ImageIO.read(input);  
            QRCodeDecoder decoder = new QRCodeDecoder();
            content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");   
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());  
            dfe.printStackTrace();  
        }  
        return content;  
    }

    //本地方法
    public static void main(String[] args) {
        MyQRCode m = new MyQRCode();
        m.encoderQRCode( "123456", "url地址做为流传输", "jpeg");
    }
        //web的常用方法
//    //测试方法1
//    public void qrtest1() throws IOException{
//        OutputStream web = this.getResponse().getOutputStream();
//        this.getResponse().setContentType("image/png");
//        MyQRCode m = new MyQRCode();
//        m.encoderQRCode( "123456", web, "jpeg");
//        web.flush();
//        web.close();
//
//        this.renderNull();
//    }
//    //测试方法1
//    public void qrtest2() throws IOException{
//        OutputStream web = this.getResponse().getOutputStream();
//        this.getResponse().setContentType("image/png");
//        MyQRCode m = new MyQRCode();
//        String path = this.getSession().getServletContext().getRealPath("") ;
//        m.encodeQRCodeAndImage( path, WxConfiger.ServerUrl + "/reg1/push?type", web, "jpeg", 5 );
//
//        web.flush();
//        web.close();
//
//        this.renderNull();
//    }

    class TwoDimensionCodeImage implements QRCodeImage {

        BufferedImage bufImg;

        public TwoDimensionCodeImage(BufferedImage bufImg) {
            this.bufImg = bufImg;
        }

        public int getHeight() {

            return bufImg.getHeight();
        }

        public int getPixel(int x, int y) {
            return bufImg.getRGB(x, y);
        }

        public int getWidth() {
            return bufImg.getWidth();
        }

    }
}  