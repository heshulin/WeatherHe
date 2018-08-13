package com.iheshulin.weather.util;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * Created by HeShulin on 2017/12/1.
 */
public  class BaiduWord {
    public static String handleText(String text){


        try {



            JSONObject keyWordJson = JSONObject.parseObject(KeyWords.getKeyWords(text));
            System.out.println(keyWordJson+"asdasdasdasdasdas");
            String word = keyWordJson.getJSONObject("showapi_res_body").getJSONArray("keywords").getString(0);

            //String word = ""+text.charAt(1)+text.charAt(2);

            String endResult = word +"表情包";

            Crawler crawler = new Crawler();
            try {
                crawler.pullPhoto(endResult);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return crawler.getPhotourl();

        }catch (Exception e){


            String endResult = text +"表情包";

            Crawler crawler = new Crawler();
            try {
                crawler.pullPhoto(endResult);
            } catch (IOException e2) {
                e.printStackTrace();
            }

            return crawler.getPhotourl();
        }

    }
}
