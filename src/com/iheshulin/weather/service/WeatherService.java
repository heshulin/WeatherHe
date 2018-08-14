package com.iheshulin.weather.service;

import com.iheshulin.weather.util.XinzhiUtil;
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
        NutMap re = new NutMap();
        try {
            now = XinzhiUtil.generateGetNowWeather(location,"zh-Hans","c");
            re.put("info", Json.fromJson(now));
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
            re.put("info",Json.fromJson(daily));
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
