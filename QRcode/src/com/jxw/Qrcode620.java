package com.jxw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class Qrcode620 {
	public static void creatCode(String content, String imgPath, int version, String startRgb, String endRgb) throws IOException{
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeVersion(version);
	     System.out.println("版本号： "+qrcode.getQrcodeVersion());
	     qrcode.setQrcodeErrorCorrect('Q');
		qrcode.setQrcodeEncodeMode('B');
		System.out.println("可存储类型："+qrcode.getQrcodeEncodeMode());
		int imgSize = 67 + (version - 1)*12;	
		BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_BGR);
	     Graphics2D gs = bufferedImage.createGraphics();
         gs.setBackground(Color.WHITE);
         gs.setColor(Color.BLACK);
         gs.clearRect(0, 0, imgSize, imgSize);
         boolean [][] calQrcode = qrcode.calQrcode(content.getBytes("UTF-8"));
		int pixoff = 2;
		int startR = 0,startG = 0,startB = 0;
		int endR = 0,endG = 0,endB = 0;
		if (null != endRgb) {
			String [] rgb = startRgb.split(",");
			startR = Integer.valueOf(rgb[0]);
			startG = Integer.valueOf(rgb[1]);
			startB = Integer.valueOf(rgb[2]);
		}
		if (null != startRgb) {
			String [] rgb = endRgb.split(",");
			endR = Integer.valueOf(rgb[0]);
			endG = Integer.valueOf(rgb[1]);
			endB = Integer.valueOf(rgb[2]);
		}
		Random random = new Random();
		for (int i = 0; i < calQrcode.length; i++) {//遍历行
			System.out.println("行数:  "+calQrcode.length+ "   列数：  "+ calQrcode[i].length);
			for (int j = 0; j < calQrcode.length; j++) {//遍历列
				if (calQrcode [i][j]) {
					int r = startR + (endR - startR)*(j+1)/calQrcode.length;
					int g = startG + (endG - startG)*(j+1)/calQrcode.length;
					int b = startB + (endB - startB)*(j+1)/calQrcode.length;
					Color color = new Color(r,g,b);
					gs.setColor(color);
					gs.fillRect(i*3+pixoff, j*3+pixoff,3,3);
				}
			}
		}
	
		BufferedImage logo = ImageIO.read(new File("D:/logo.jpg"));
		int logoSize = imgSize/3;
        int o = (imgSize - logoSize)/2;
        gs.drawImage(logo, o, o, logoSize,logoSize, null);
		gs.dispose();
	    bufferedImage.flush();
		try {
			ImageIO.write(bufferedImage, "png", new File("D:/123.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("完成");
	}

	 public static void main(String[] args) throws IOException {
		String imgPath = "D:/qrcode.png"; 
		int version = 20;
		String content ="BEGIN:VCARD\r\n" + 
		   "FN:姓名:杨莎莎\r\n"+
		   "ORG:学校:河北科技师范学院	部门:科技\r\n"+
		   "TITLE:学生\r\n" + 
		   "TEL;CELL;VOICE:18811112222\r\n"+
		   "ADR;WORK:河北科技师范学院\r\n"+
		   "ADR;HOME:南院6号楼120宿舍\r\n"+
		   "EMAIL;HOME:1041531529@qq.com\r\n" + 
		   "END:VCARD";
		String startRgb = "0,200,0";
		String endRgb = "115,0,165";
		creatCode(content,imgPath,version,startRgb,endRgb);
		
	}

}
