package com.iheshulin.weather.util;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by HeShulin on 2018/8/16.
 */
public class ImageUtil {
    public static String srcImgPath="e:\\y.png"; //源图片地址
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






            int x,y;
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(new Color(255,255,255));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.9));//透明度
            //温度
            String waterMarkContent=todayState.getString("temperature");  //水印内容
            Font font = new Font("微软雅黑", Font.PLAIN, 55);
            g.setFont(font);              //设置字体
            x = 20;
            y = 80;
            g.drawString(waterMarkContent, x, y);  //画出水印

            //摄氏度符号
            String sign="℃";  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 20);
            g.setFont(font);              //设置字体
            x = 80;
            y = 55;
            g.drawString(sign, x, y);  //画出水印

            //地址
            String locationtext=todaylocation.getString("name");  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 20);
            g.setFont(font);              //设置字体
            x = 20;
            y = 105;
            g.drawString(locationtext, x, y);  //画出水印


            //天气
            String weathernow=todayState.getString("text");  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 20);
            g.setFont(font);              //设置字体
            x = 20;
            y = 130;
            g.drawString(weathernow, x, y);  //画出水印

            //左分号
            String left="“";  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 40);
            g.setFont(font);              //设置字体
            x = 10;
            y = 330;
            g.drawString(left, x, y);  //画出水印
            //右分号
            String right="”";  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 40);
            g.setFont(font);              //设置字体
            x = 280;
            y = 400;
            g.drawString(right, x, y);  //画出水印

            String[] poem = PoemUtil.PlanC("晴天").split("，");
            //左诗句

            font = new Font("微软雅黑", Font.PLAIN, 20);
            g.setFont(font);              //设置字体
            x = 60;
            y = 325;
            g.drawString(poem[0], x, y);  //画出水印
            //右诗句
            font = new Font("微软雅黑", Font.PLAIN, 20);
            g.setFont(font);              //设置字体
            x = 130;
            y = 370;
            g.drawString(poem[1], x, y);  //画出水印

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.3));
            //劈叉天气
            String picha="劈叉天气";  //水印内容
            font = new Font("微软雅黑", Font.PLAIN, 12);
            g.setColor(new Color(246,246,246)); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体
            x = 140;
            y = 450;
            g.drawString(picha, x, y);  //画出水印

            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();

            UploadFile uploadFile = new UploadFile();
            return uploadFile.uploadSpeech("e:\\ans.png");

        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }

}
