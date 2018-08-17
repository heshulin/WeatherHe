package com.iheshulin.weather.util;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.util.HashMap;

/**
 * Created by HeShulin on 2018/8/14.
 */
public class TTSUtil {
    //设置APPID/AK/SK
    public static final String APP_ID = "11676480";
    public static final String API_KEY = "HGiP2GF9q7KyYDr3xNI9G3OG";
    public static final String SECRET_KEY = "gyr3Oi8Ngpb1zntBTt6kIco3Q3SNAKCh";

    public static String textToSpeech(String location,String locationUrl) throws Exception {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // synthesis(client);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        String speechText = location+"市，今天白天";


        String dailyWeatherString = XinzhiUtil.generateGetDiaryWeather(
                location,
                "zh-Hans",
                "c",
                "0",
                "1"
        );
        String nowAirString = XinzhiUtil.generateGetNowAir(
                location,
                "zh-Hans",
                "c"
        );
        String suggestionLifeString = XinzhiUtil.generateGetSuggestionLife(
                location,
                "zh-Hans",
                "c"
        );
        //解析airjson
        JSONObject air = new JSONObject(nowAirString);
        JSONArray airAnsArray =  air.getJSONArray("results");
        JSONObject airAns =  airAnsArray.getJSONObject(0);
        JSONObject todayAir = airAns.getJSONObject("air");
        JSONObject todayAirState = todayAir.getJSONObject("city");
        //解析weatherjson
        JSONObject weather = new JSONObject(dailyWeatherString);
        JSONArray weatherAnsArray =  weather.getJSONArray("results");
        JSONObject weatherAns = weatherAnsArray.getJSONObject(0);
        JSONArray todayStateArray = weatherAns.getJSONArray("daily");
        JSONObject todayState = todayStateArray.getJSONObject(0);
        //解析weatherjson
        JSONObject life = new JSONObject(suggestionLifeString);
        JSONArray lifeAnsArray =  life.getJSONArray("results");
        JSONObject lifeAns = lifeAnsArray.getJSONObject(0);
        JSONObject lifeSuggestionState = lifeAns.getJSONObject("suggestion");
        JSONObject lifeState = lifeSuggestionState.getJSONObject("umbrella");
        //白天天气气象
        String text_day = todayState.getString("text_day");
        //夜晚天气气象
        String text_night = todayState.getString("text_night");
        //最高温度
        String high = todayState.getString("high");
        //最低温度
        String low = todayState.getString("low");
        //风向
        String wind_direction = todayState.getString("wind_direction");
        //风力等级
        String wind_scale = todayState.getString("wind_scale");
        //雨伞建议
        String umbrella_details = lifeState.getString("details");

        //空气质量
        String quality = todayAirState.getString("quality");
        speechText = speechText + text_day + ",夜晚" + text_night + ",最高温度" + high + "℃,最低温度" + low + "℃," +
                wind_direction + "风" + wind_scale + "级" + ",空气质量" + "," + quality+"。今天"+umbrella_details;
        System.out.println(speechText);
        // 调用接口
        TtsResponse res = client.synthesis(speechText, "zh", 1, null);
        byte[] data = res.getData();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data ,locationUrl);
                return locationUrl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getSpeechUrl(String location) throws Exception {
        String locationUrl = textToSpeech(location ,"output.mp3");
        if(!"".equals(locationUrl)){
            UploadFile uploadFile = new UploadFile();
            String uploadUrl = uploadFile.uploadSpeech(locationUrl);
            return uploadUrl;
        }
        else
        {
            return "";
        }
    }


    public static String getAISpeechUrl(String p) throws Exception {
        String locationUrl = AItoSpeech(p ,"outputAI.mp3");
        if(!"".equals(locationUrl)){
            UploadFile uploadFile = new UploadFile();
            String uploadUrl = uploadFile.uploadSpeech(locationUrl);
            return uploadUrl;
        }
        else
        {
            return "";
        }
    }

    public static String AItoSpeech(String p,String locationUrl) throws Exception {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // synthesis(client);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");


        // 调用接口
        TtsResponse res = client.synthesis(p, "zh", 1, null);
        byte[] data = res.getData();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data ,locationUrl);
                return locationUrl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


}