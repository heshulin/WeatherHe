package com.iheshulin.weather.util;

import okio.Buffer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.AttributedCharacterIterator;

/**
 * Created by HeShulin on 2018/8/16.
 */
public class ImageUtil {
    public static String sunsrcImgPath="e:\\sun.png"; //源图片地址
    public static String cloudsrcImgPath="e:\\cloud.png"; //源图片地址
    public static String rainsrcImgPath="e:\\rain.png"; //源图片地址
    public static String snowsrcImgPath="e:\\snow.png"; //源图片地址
    public static String tarImgPath="e:\\ans.png"; //待存储的地址

    public static String addWaterMark(String location) {
        try {
            //解析weatherjson
            String now = XinzhiUtil.generateGetNowWeather(location,"zh-Hans","c");
            JSONObject weather = new JSONObject(now);
            JSONArray weatherAnsArray =  weather.getJSONArray("results");
            JSONObject weatherAns = weatherAnsArray.getJSONObject(0);
            JSONObject todayState = weatherAns.getJSONObject("now");
            JSONObject todaylocation = weatherAns.getJSONObject("location");


            int  weatherCode = Integer.parseInt(todayState.getString("code"));  //水印内容
            File srcImgFile = null;//得到文件
            System.out.println(weatherCode);
            int colorflag = 0;
            if(weatherCode==0||weatherCode==1||weatherCode==2||weatherCode==3||weatherCode==99){
                srcImgFile = new File(sunsrcImgPath);
                colorflag = 1;
            }
            else if(weatherCode==4||weatherCode==5||weatherCode==6||weatherCode==7||weatherCode==8||weatherCode==9||weatherCode==26||weatherCode==27||weatherCode==28||weatherCode==29||weatherCode==30||weatherCode==31||weatherCode==32||weatherCode==33||weatherCode==34||weatherCode==35||weatherCode==36||weatherCode==37||weatherCode==38){
                srcImgFile = new File(cloudsrcImgPath);
                colorflag = 2;
            }
            else if(weatherCode==10||weatherCode==11||weatherCode==12||weatherCode==13||weatherCode==14||weatherCode==15||weatherCode==16||weatherCode==17||weatherCode==18||weatherCode==19||weatherCode==20){
                srcImgFile = new File(rainsrcImgPath);
                colorflag = 3;
            }
            else if(weatherCode==21||weatherCode==22||weatherCode==23||weatherCode==24||weatherCode==25){
                srcImgFile = new File(snowsrcImgPath);
                colorflag = 4;
            }
            int x,y;

            // 读取原图片信息
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            System.out.println("testnext");
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(new Color(255,255,255));
            //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.9));//透明度

            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);


            if(colorflag==3){
                g.setColor(new Color(65,82,208));
            }
            else if(colorflag==2){
                g.setColor(new Color(0,150,136));
            }
            else {
                g.setColor(new Color(255,255,255));
            }
            //温度
            String waterMarkContent=todayState.getString("temperature");  //水印内容
            Font font = new Font("微软雅黑", Font.PLAIN, 70);
            g.setFont(font);              //设置字体
            x = 20;
            y = 110;

            g.setColor(new Color(127,215,167));
            g.drawString(waterMarkContent, x+2, y+2);//加阴影
            if(colorflag==3){
                g.setColor(new Color(65,82,208));
            }
            else if(colorflag==2){
                g.setColor(new Color(0,150,136));
            }
            else {
                g.setColor(new Color(255,255,255));
            }
            g.drawString(waterMarkContent, x, y);  //画出水印
            //摄氏度符号
            String sign="℃";  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 25);
            g.setFont(font);              //设置字体
            x = 95;
            y = 75;
            g.drawString(sign, x, y);  //画出水印

            //地址
            String locationtext=todaylocation.getString("name");  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 27);
            g.setFont(font);              //设置字体
            x = 25;
            y = 140;
            g.drawString(locationtext, x, y);  //画出水印


            //天气
            String weathernow=todayState.getString("text");  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 15);
            g.setFont(font);              //设置字体
            x = 26;
            y = 160;
            g.drawString(weathernow, x, y);  //画出水印

            if(colorflag==1||colorflag==3||colorflag==4)
            {
                g.setColor(new Color(255,255,255));
            }else
            {
                g.setColor(new Color(0,150,136));
            }
//            //左分号
//            String left="“";  //水印内容
//            font = new Font("微软雅黑", Font.PLAIN, 40);
//            g.setFont(font);              //设置字体
//            x = 10;
//            y = 330;
//            g.drawString(left, x, y);  //画出水印
//
//            //右分号
//            String right="”";  //水印内容
//            font = new Font("微软雅黑", Font.PLAIN, 40);
//            g.setFont(font);              //设置字体
//            x = 280;
//            y = 400;
//            g.drawString(right, x, y);  //画出水印
            String[] poem = PoemUtil.PlanC("晴天").split("，");



            //左诗句
            font = new Font("仿宋", Font.PLAIN, 22);
            g.setFont(font);              //设置字体
            x = 10;
            y = 325;
            g.setColor(new Color(127,215,167));
            g.drawString("“"+poem[0], x+1, y+1);//加阴影
            if(colorflag==1||colorflag==3||colorflag==4)
            {
                g.setColor(new Color(255,255,255));
            }else
            {
                g.setColor(new Color(0,150,136));
            }

            g.drawString("“"+poem[0], x, y);  //画出水印
            //右诗句
            font = new Font("仿宋", Font.PLAIN, 22);
            g.setFont(font);              //设置字体
            x = 31;
            y = 350;

            g.setColor(new Color(127,215,167));
            g.drawString(poem[1]+"”", x+1, y+1);//加阴影
            if(colorflag==1||colorflag==3||colorflag==4)
            {
                g.setColor(new Color(255,255,255));
            }else {
                g.setColor(new Color(0,150,136));
            }


            g.drawString(poem[1]+"”", x, y);  //画出水印
            //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.3));


            g.setColor(new Color(255,255,255));

            //劈叉天气
            g.drawLine(123,464,132,464);
            g.drawLine(190,464,198,464);
            String picha="劈叉天气";  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 12);
            g.setColor(new Color(246,246,246)); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体
            x = 138;
            y = 470;
            g.drawString(picha, x, y);  //画出水印
            g.dispose();

            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();
            UploadFile uploadFile = new UploadFile();
            System.out.println(uploadFile.uploadSpeech(tarImgPath));
            return uploadFile.uploadSpeech(tarImgPath);

        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }

}
