package com.iheshulin.weather.service;

import com.iheshulin.weather.util.XinzhiUtil;
import org.json.JSONObject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by HeShulin on 2018/8/13.
 */
@IocBean
public class AirService {

    //空气质量实况
    @Ok("json")
    @Fail("http:500")
    @At("/getnowair")
    @GET
    public Object getNowAir(@Param("location")String location, HttpServletRequest request){
        String now = null;
        NutMap re = new NutMap();
        try {
            now = XinzhiUtil.generateGetNowAir(location, "zh-Hans", "c");
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

    //空气质量实况城市排行
    @Ok("json")
    @Fail("http:500")
    @At("/getrankingair")
    @GET
    public Object getRankingAir( HttpServletRequest request){
        String ranking = null;
        NutMap re = new NutMap();
        try {
            ranking = XinzhiUtil.generateGetRankingAir("zh-Hans", "c");
            re.put("info",Json.fromJson(ranking));
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
    //逐日空气质量预报
    @Ok("json")
    @Fail("http:500")
    @At("/getdailyair")
    @GET
    public Object getDailyAir(@Param("location")String location, HttpServletRequest request){
        String daily = null;
        NutMap re = new NutMap();
        try {
            daily = XinzhiUtil.generateGetDailyAir(location,"zh-Hans", "c","7");
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

    //逐小时空气质量预报
    @Ok("json")
    @Fail("http:500")
    @At("/gethourlyair")
    @GET
    public Object getHourlyAir(@Param("location")String location, HttpServletRequest request){
        String hourly = null;
        NutMap re = new NutMap();
        try {
            hourly = XinzhiUtil.generateGetHourlyAir(location,"zh-Hans", "c","1");
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


}
