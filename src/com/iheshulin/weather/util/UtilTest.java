package com.iheshulin.weather.util;


import org.json.JSONObject;

/**
 * Created by HeShulin on 2018/8/11.
 */
public class UtilTest {

    public static void main(String args[]){
        try {

//            daily
//            String daily = XinzhiUtil.generateGetNowAir(
//                    "上海",
//                    "zh-Hans",
//                    "c"
//                    );
//
//            System.out.println("ANS:" + new JSONObject(daily).toString(4));
            //poem

//            //PLANA只能一个字
//            String poema = PoemUtil.PlanA("阴");
//            System.out.println("PlanAPoem:"+poema);
//            //PLANB只能两个字
//            String poemb = PoemUtil.PlanB("晴天");
//            System.out.println("PlanBPoem:"+poemb);
            //PLANC 随便
//            String poemc = PoemUtil.PlanC("晴天");
//            System.out.println("PlanCPoem:"+poemc);
            //daily

            System.out.println("ANS:" + TTSUtil.getSpeechUrl("北京"));

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }
}
