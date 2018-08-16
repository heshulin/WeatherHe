package com.iheshulin.weather.service;

import com.alibaba.fastjson.JSON;
import com.iheshulin.weather.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
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
    //得到古诗
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
    //简要生活指数
    @Ok("json")
    @Fail("http:500")
    @At("/getbriefsuggestionlife")
    @GET
    public Object getBriefSuggestionLife(@Param("location")String location, HttpServletRequest request){
        String suggestionLifeStr = null;
        String calendarLifeStr = null;
        NutMap re = new NutMap();
        try {

            suggestionLifeStr = XinzhiUtil.generateGetSuggestionLife(location,"zh-Hans", "c");
            calendarLifeStr = XinzhiUtil.generateGetCalendarLife(location,"zh-Hans", "c","0","1");
            //农历日期获取
            JSONObject calendarLifeJson = new JSONObject(calendarLifeStr);
            JSONObject calendarLifeJsonResults = calendarLifeJson.getJSONObject("results");
            JSONArray calendarLifeJsonList = calendarLifeJsonResults.getJSONArray("chinese_calendar");
            JSONObject calendarLife = calendarLifeJsonList.getJSONObject(0);
            //农历日期获取
            String monthday =  calendarLife.getString("lunar_month_name")+calendarLife.getString("lunar_day_name");
            //生活指数json处理
            JSONObject suggestionLifeJson = new JSONObject(suggestionLifeStr);
            JSONArray suggestionLifeJsonArray = suggestionLifeJson.getJSONArray("results");
            JSONObject suggestionLifeJsonList0 = suggestionLifeJsonArray.getJSONObject(0);
//            JSONObject suggestion = suggestionLifeJsonList0.getJSONObject("suggestion");
            JSONObject suggestioninfo = suggestionLifeJsonList0.getJSONObject("suggestion");
            //穿衣
            String dressing = suggestioninfo.getJSONObject("dressing").getString("brief");
            //紫外线
            String uv = suggestioninfo.getJSONObject("uv").getString("brief");
            //运动
            String sport = suggestioninfo.getJSONObject("sport").getString("brief");
            //空气质量
            String air_pollution = suggestioninfo.getJSONObject("mood").getString("brief");
            //路况
            String traffic = suggestioninfo.getJSONObject("traffic").getString("brief");
            //外出
            String goout = suggestioninfo.getJSONObject("dating").getString("brief");
            //美妆
            String makeup = suggestioninfo.getJSONObject("makeup").getString("brief");
            System.out.println();

            //构造返回JSON
            JSONObject suggestionLife = new JSONObject();
            suggestionLife.append("date", monthday);
            suggestionLife.append("dressing", dressing);
            suggestionLife.append("uv", uv);
            suggestionLife.append("sport", sport);
            suggestionLife.append("air_pollution", air_pollution);
            suggestionLife.append("traffic", traffic);
            suggestionLife.append("goout", goout);
            suggestionLife.append("makeup", makeup);

            re.put("info",Json.fromJson(suggestionLife.toString().replace("[","").replace("]","")));
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

    //语音播报
    @Ok("json")
    @Fail("http:500")
    @At("/getspeech")
    @GET
    public Object getSpeech(@Param("location")String location, HttpServletRequest request){
        String speech = null;
        NutMap re = new NutMap();
        try {
            speech = TTSUtil.getSpeechUrl(location);
            if(!"".equals(speech)){
                re.put("info",speech);
                re.put("state",1);
                re.put("msg","Gain success");
            }else
            {
                re.put("info",speech);
                re.put("state",0);
                re.put("msg","net error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            re.put("info","");
            re.put("state",0);
            re.put("msg","Acquisition failed");
        }
        return re;
    }
    //得到分享图片
    @Ok("json")
    @Fail("http:500")
    @At("/getshareimage")
    @GET
    public Object getShareImage(@Param("location")String location, HttpServletRequest request){
        String shareimage = null;
        NutMap re = new NutMap();
        try {
            shareimage = ImageUtil.addWaterMark(location);
            re.put("info",shareimage);
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
