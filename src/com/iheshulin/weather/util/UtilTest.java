package com.iheshulin.weather.util;


import org.json.JSONObject;

/**
 * Created by HeShulin on 2018/8/11.
 */
public class UtilTest {
    public static void main(String args[]){
        XinzhiUtil demo = new XinzhiUtil();
        try {

            //daily
//            String daily = XinzhiUtil.generateGetDiaryWeather(
//                    "shanghai",
//                    "zh-Hans",
//                    "c",
//                    "1",
//                    "1");
//
//            System.out.println("ANS:" + new JSONObject(daily).toString(4));
            //now
//            String now = XinzhiUtil.generateGetNowWeather(
//                    "shanghai",
//                    "zh-Hans",
//                    "c");
//
//
//            System.out.println("ANS:" + new JSONObject(now).toString(4));
            //grid
//            String gridnow = XinzhiUtil.generateGetGridNowWeather(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c");
//
//
//            System.out.println("ANS:" + new JSONObject(gridnow).toString(4));
            //gridminutely
            String Minutely = XinzhiUtil.generateGetGridMinutelyWeather(
                    "北京",
                    "zh-Hans",
                    "c");


            System.out.println("ANS:" + new JSONObject(Minutely).toString(4));
            //hourly
//            String hourly = XinzhiUtil.generateGetGridHourlyWeather(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c",
//                    "0",
//                    "24"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(hourly).toString(4));
            //ALARM
//            String alarm = XinzhiUtil.generateGetAlarmWeather(
//                    "",
//                    "zh-Hans",
//                    "c"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(alarm).toString(4));
            //airnow
//            String airnow = XinzhiUtil.generateGetNowAir(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(airnow).toString(4));
            //airranking
//            String airranking = XinzhiUtil.generateGetRankingAir(
//                    "zh-Hans",
//                    "c"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(airranking).toString(4));

            //airdaily
//            String airdaily = XinzhiUtil.generateGetDailyAir(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c",
//                    "5"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(airdaily).toString(4));


            //airhourly
//            String airhour = XinzhiUtil.generateGetHourlyAir(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c",
//                    "1"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(airhour).toString(4));


            //lifesuggestion
//            String lifesuggestion = XinzhiUtil.generateGetSuggestionLife(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(lifesuggestion).toString(4));

            //sungeo
//            String sungeo = XinzhiUtil.generateGetSunGeo(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c",
//                    "0",
//                    "2"
//            );
//
//
//            System.out.println("ANS:" + new JSONObject(sungeo).toString(4));

            //moongeo
//            String moongeo = XinzhiUtil.generateGetMoonGeo(
//                    "39.93:116.40",
//                    "zh-Hans",
//                    "c",
//                    "0",
//                    "2"
//            );
//
//            System.out.println("ANS:" + new JSONObject(moongeo).toString(4));

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }
}
