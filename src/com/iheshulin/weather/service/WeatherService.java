package com.iheshulin.weather.service;

import com.iheshulin.weather.util.DateConvertorUtil;
import com.iheshulin.weather.util.XinzhiUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeShulin on 2018/8/13.
 */
@IocBean
public class WeatherService {



    //得到现在的实况天气
    @Ok("json")
    @Fail("http:500")
    @At("/getnowweather")
    @GET
    public Object getNowWeather(@Param("location")String location,HttpServletRequest request){
        String now = null;
        String air = null;
        NutMap re = new NutMap();
        try {
            now = XinzhiUtil.generateGetNowWeather(location,"zh-Hans","c");
            air = XinzhiUtil.generateGetNowAir(location,"zh-Hans","c");

            //解析airjson
            JSONObject airjson = new JSONObject(air);
            JSONArray airAnsArray =  airjson.getJSONArray("results");
            JSONObject airAns =  airAnsArray.getJSONObject(0);
            JSONObject todayAir = airAns.getJSONObject("air");
            JSONObject todayAirState = todayAir.getJSONObject("city");
            //解析weatherjson
            JSONObject weather = new JSONObject(now);
            JSONArray weatherAnsArray =  weather.getJSONArray("results");
            JSONObject weatherAns = weatherAnsArray.getJSONObject(0);
            JSONObject todayState = weatherAns.getJSONObject("now");
            JSONObject todaylocation = weatherAns.getJSONObject("location");
            String last_update = weatherAns.getString("last_update");
            //空气质量
            String quality = todayAirState.getString("quality");
            todayState.append("quality",quality);
            todayState.append("week", DateConvertorUtil.getTodayWeek());
            JSONObject tempJson = new JSONObject();
            tempJson.append("location",todaylocation);
            tempJson.append("now",todayState);
            tempJson.append("last_update",last_update);
            JSONObject results = new JSONObject();
            results.append("results",Json.fromJson(tempJson.toString().replace("[","").replace("]","")));
            re.put("info", Json.fromJson(results.toString()));
            re.put("state",1);
            re.put("msg","Gain success");
        } catch (Exception e) {
            e.printStackTrace();
            re.put("info","");
            re.put("state",0);
            re.put("msg","Acquisition failed");
        }
        return re;
    }


    //得到n天的天气预报
    @Ok("json")
    @Fail("http:500")
    @At("/getdailyweather")
    @GET
    public Object getDailyWeather(@Param("location")String location,HttpServletRequest request){
        String daily = null;
        NutMap re = new NutMap();
        try {
            daily = XinzhiUtil.generateGetDiaryWeather(location, "zh-Hans", "c", "0", "7");
            //解析dailyjson
            JSONObject weather = new JSONObject(daily);
            JSONArray weatherAnsArray =  weather.getJSONArray("results");
            JSONObject weatherAns = weatherAnsArray.getJSONObject(0);
            JSONArray dailyState = weatherAns.getJSONArray("daily");
            JSONObject dailylocation = weatherAns.getJSONObject("location");
            String last_update = weatherAns.getString("last_update");
            JSONArray newdaily = new JSONArray();
            for(int i=0;i<7;i++){
                JSONObject tempobject = dailyState.getJSONObject(i);
                tempobject.append("week",DateConvertorUtil.dateToWeek(tempobject.getString("date")));
                newdaily.put(new JSONObject(tempobject.toString().replace("[","").replace("]","")));
            }
            JSONObject tempJson = new JSONObject();
            tempJson.append("location",dailylocation);
            tempJson.put("daily",newdaily);
            tempJson.append("last_update",last_update);
            JSONObject results = new JSONObject();
            results.append("results",Json.fromJson(tempJson.toString()));
            re.put("info",Json.fromJson(results.toString()));
            re.put("state",1);
            re.put("msg","Gain success");
        } catch (Exception e) {
            e.printStackTrace();
            re.put("info","");
            re.put("state",0);
            re.put("msg","Acquisition failed");
        }
        return re;
    }

    //关注的多个城市的天气实况
    @Ok("json")
    @Fail("http:500")
    @At("/getlistnowweather")
    @GET
    public Object getListNowWeather(@Param("locationlist")String locationlist,HttpServletRequest request){
        List<String> listnow;
        List<Object> ansstr2 = new ArrayList<Object>();
        NutMap re = new NutMap();
        try {
            listnow = java.util.Arrays.asList(locationlist.split(","));
            for(int i = 0 ;i<listnow.size();i++){
                ansstr2.add(Json.fromJson(XinzhiUtil.generateGetNowWeather(listnow.get(i), "zh-Hans", "c")));
            }
            re.put("info",ansstr2);
            re.put("state",1);
            re.put("msg","Gain success");
        } catch (Exception e) {
            e.printStackTrace();
            re.put("info","");
            re.put("state",0);
            re.put("msg","Acquisition failed");
        }
        return re;
    }


    //分钟级降水预报(error)
    @Ok("json")
    @Fail("http:500")
    @At("/getminutelyweather")
    @GET
    public Object getminutelyweather(@Param("location")String location,HttpServletRequest request){
        String minutely = null;
        NutMap re = new NutMap();
        try {
            minutely = XinzhiUtil.generateGetGridMinutelyWeather(location, "zh-Hans", "c");
            re.put("info",Json.fromJson(minutely));
            re.put("state",1);
            re.put("msg","Gain success");
        } catch (Exception e) {
            e.printStackTrace();
            re.put("info","");
            re.put("state",0);
            re.put("msg","Acquisition failed");
        }
        return re;
    }


    //24小时逐小时天气预报
    @Ok("json")
    @Fail("http:500")
    @At("/gethourlyweather")
    @GET
    public Object gethourlyweather(@Param("location")String location,HttpServletRequest request){
        String hourly = null;
        NutMap re = new NutMap();
        try {
            hourly = XinzhiUtil.generateGetHourlyWeather(location, "zh-Hans", "c","0","24");
            re.put("info",Json.fromJson(hourly));
            re.put("state",1);
            re.put("msg","Gain success");
        } catch (Exception e) {
            e.printStackTrace();
            re.put("info","");
            re.put("state",0);
            re.put("msg","Acquisition failed");
        }
        return re;
    }


    //气象灾害预警
    @Ok("json")
    @Fail("http:500")
    @At("/getalarmweather")
    @GET
    public Object getAlarmWeather(@Param("location")String location,HttpServletRequest request){
        String alarm = null;
        NutMap re = new NutMap();
        try {
            alarm = XinzhiUtil.generateGetAlarmWeather(location, "zh-Hans", "c");
            re.put("info",Json.fromJson(alarm));
            re.put("state",1);
            re.put("msg","Gain success");
        } catch (Exception e) {
            e.printStackTrace();
            re.put("info","");
            re.put("state",0);
            re.put("msg","Acquisition failed");
        }
        return re;
    }
}
