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
    @Ok("json")
    @Fail("http:500")
    @At("/getpoem")
    @GET
    public Object getPoem(@Param("poemtitle")String poemtitle, HttpServletRequest request){
        String poem = null;
        NutMap re = new NutMap();
        try {
            poem = PoemUtil.PlanC(java.net.URLEncoder.encode(poemtitle));
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
}
