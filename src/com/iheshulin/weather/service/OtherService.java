package com.iheshulin.weather.service;

import com.iheshulin.weather.util.PoemUtil;
import com.iheshulin.weather.util.XinzhiUtil;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by HeShulin on 2018/8/14.
 */
@IocBean
public class OtherService {
    //空气质量实况
    @Ok("json:")
    @Fail("http:500")
    @At("/getpoem")
    @GET
    public Object getPoem(@Param("poemtitle")String poemtitle, HttpServletRequest request){
        String poem = null;
        NutMap re = new NutMap();
        try {
            poem = PoemUtil.PlanC(poemtitle);
            re.put("info",poem);
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

    //生活指数
    @Ok("json")
    @Fail("http:500")
    @At("/getsuggestionlife")
    @GET
    public Object getSuggestionLife(@Param("location")String location, HttpServletRequest request){
        String suggestionLife = null;
        NutMap re = new NutMap();
        try {
            suggestionLife = XinzhiUtil.generateGetSuggestionLife(location,"zh-Hans", "c");
            re.put("info",Json.fromJson(suggestionLife));
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

    //日出日落
    @Ok("json")
    @Fail("http:500")
    @At("/getsungeo")
    @GET
    public Object getSunGeo(@Param("location")String location, HttpServletRequest request){
        String sungeo = null;
        NutMap re = new NutMap();
        try {
            sungeo = XinzhiUtil.generateGetSunGeo(location,"zh-Hans", "c","0","1");
            re.put("info",Json.fromJson(sungeo));
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

    //月出月落月相
    @Ok("json")
    @Fail("http:500")
    @At("/getmoongeo")
    @GET
    public Object getMoonGeo(@Param("location")String location, HttpServletRequest request){
        String moongeo = null;
        NutMap re = new NutMap();
        try {
            moongeo = XinzhiUtil.generateGetMoonGeo(location,"zh-Hans", "c","0","1");
            re.put("info",Json.fromJson(moongeo));
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


    //农历、节气、生肖
    @Ok("json")
    @Fail("http:500")
    @At("/getcalendarlife")
    @GET
    public Object getCalendarLife(@Param("location")String location, HttpServletRequest request){
        String calendarlife = null;
        NutMap re = new NutMap();
        try {
            calendarlife = XinzhiUtil.generateGetCalendarLife(location,"zh-Hans", "c","0","1");
            re.put("info",Json.fromJson(calendarlife));
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


    //机动车尾号限行
    @Ok("json")
    @Fail("http:500")
    @At("/getdrivinglife")
    @GET
    public Object getDrivingLife(@Param("location")String location, HttpServletRequest request){
        String drivinglife = null;
        NutMap re = new NutMap();
        try {
            drivinglife = XinzhiUtil.generateGetDrivingLife(location,"zh-Hans", "c");
            re.put("info",Json.fromJson(drivinglife));
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
