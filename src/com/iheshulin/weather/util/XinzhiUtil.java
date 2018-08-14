package com.iheshulin.weather.util;

/**
 * Created by HeShulin on 2018/8/11.
 */

import org.nutz.http.Http;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;

public class XinzhiUtil {




    private static String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";
    private static String TIANQI_GRIDNOW_WEATHER_URL = "https://api.seniverse.com/v3/weather/grid/now.json";
    private static String TIANQI_NOW_WEATHER_URL = "https://api.seniverse.com/v3/weather/now.json";
    private static String TIANQI_MINUTELY_WEATHER_URL = "https://api.seniverse.com/v3/weather/grid/minutely.json";
    private static String TIANQI_HOURLY_WEATHER_URL = "https://api.seniverse.com/v3/weather/hourly.json";
    private static String TIANQI_ALARM_WEATHER_URL = "https://api.seniverse.com/v3/weather/alarm.json";
    private static String TIANQI_NOW_AIR_URL = "https://api.seniverse.com/v3/air/now.json";
    private static String TIANQI_RANKING_AIR_URL = "https://api.seniverse.com/v3/air/ranking.json";
    private static String TIANQI_DAILY_AIR_URL = "https://api.seniverse.com/v3/air/daily.json";
    private static String TIANQI_HOURLY_AIR_URL = "https://api.seniverse.com/v3/air/hourly.json";
    private static String TIANQI_SUGGESTION_LIFE_URL = "https://api.seniverse.com/v3/life/suggestion.json";
    private static String TIANQI_SUN_GEO_URL = "https://api.seniverse.com/v3/geo/sun.json";
    private static String TIANQI_MOON_GEO_URL = "https://api.seniverse.com/v3/geo/moon.json";
    private static String TIANQI_CALENDAR_LIFE_URL = "https://api.seniverse.com/v3/life/chinese_calendar.json";
    private static String TIANQI_DRIVING_LIFE_URL = "https://api.seniverse.com/v3/life/driving_restriction.json";


    private static String TIANQI_API_SECRET_KEY = "afmlz62jdx69kmph";
    private static String TIANQI_API_USER_ID = "U89982282B";

    private static String timestamp = String.valueOf(new Date().getTime());
    private static String params = "ts=" + timestamp + "&ttl=30&uid=" + TIANQI_API_USER_ID;
    private static String signature;
    static {
        try {
            signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }
    /**
     * Generate HmacSHA1 signature with given data string and key
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private static String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    //得到现在的实况天气
    public static String generateGetNowWeather(
            String location,
            String language,
            String unit
    ) throws Exception {
        String url =  TIANQI_NOW_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit;
        String now = Http.get(url).getContent();
        return now;
    }

    //得到n天的天气预报
    public static String generateGetDiaryWeather(
            String location,
            String language,
            String unit,
            String start,
            String days
    ) throws Exception {
        String url =  TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
        String daily = Http.get(url).getContent();
        return daily;
    }

    //得到现在的格点实况天气
    public static String generateGetGridNowWeather(
            String location,
            String language,
            String unit
    ) throws Exception {
        String url =  TIANQI_GRIDNOW_WEATHER_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY + "&location=" + location + "&language=" + language + "&unit=" + unit;
        String gridnow = Http.get(url).getContent();
        return gridnow;
    }



    //分钟级降水预报
    public static String generateGetGridMinutelyWeather(
            String location,
            String language,
            String unit
    ) throws Exception {
        String url =  TIANQI_MINUTELY_WEATHER_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY + "&location=" + location + "&language=" + language + "&unit=" + unit;
        String gridMinutely = Http.get(url).getContent();
        return gridMinutely;
    }

    //24小时逐小时天气预报
    public static String generateGetHourlyWeather(
            String location,
            String language,
            String unit,
            String start,
            String hours
    ) throws Exception {
        String url =  TIANQI_HOURLY_WEATHER_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&hours=" + hours;
        String hourly = Http.get(url).getContent();
        return hourly;
    }

    //气象灾害预警
    public static String generateGetAlarmWeather(
            String location,
            String language,
            String unit
    ) throws Exception {
        String url;
        if(location.equals("")) {
            url =  TIANQI_ALARM_WEATHER_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY + "&language=" + language + "&unit=" + unit ;
        }else
        {
            url =  TIANQI_ALARM_WEATHER_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY + "&location=" + location + "&language=" + language + "&unit=" + unit ;
        }
        String alarm = Http.get(url).getContent();
        return alarm;
    }

    //空气质量实况
    public static String generateGetNowAir(
            String location,
            String language,
            String unit
    ) throws Exception {
        String url =  TIANQI_NOW_AIR_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY + "&location=" + location + "&language=" + language + "&unit=" + unit ;
        String nowAir = Http.get(url).getContent();
        return nowAir;
    }

    //空气质量实况城市排行
    public static String generateGetRankingAir(
            String language,
            String unit
    ) throws Exception {
        String url =  TIANQI_RANKING_AIR_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit ;
        String rankingAir = Http.get(url).getContent();
        return rankingAir;
    }


    //逐日空气质量预报
    public static String generateGetDailyAir(
            String location,
            String language,
            String unit,
            String days
    ) throws Exception {
        String url =  TIANQI_DAILY_AIR_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit + "&location=" + location + "&days=" + days;
        String dailyAir = Http.get(url).getContent();
        return dailyAir;
    }


    //逐小时空气质量预报
    public static String generateGetHourlyAir(
            String location,
            String language,
            String unit,
            String days
    ) throws Exception {
        String url =  TIANQI_HOURLY_AIR_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit + "&location=" + location + "&days=" + days;
        String hourlyAir = Http.get(url).getContent();
        return hourlyAir;
    }

    //生活指数
    public static String generateGetSuggestionLife(
            String location,
            String language,
            String unit
    ) throws Exception {
        String url =  TIANQI_SUGGESTION_LIFE_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit + "&location=" + location ;
        String suggestionLife = Http.get(url).getContent();
        return suggestionLife;
    }

    //日出日落
    public static String generateGetSunGeo(
            String location,
            String language,
            String unit,
            String start,
            String days
    ) throws Exception {
        String url =  TIANQI_SUN_GEO_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit + "&location=" + location + "&start=" + start + "&days=" + days;
        String sunGeo = Http.get(url).getContent();
        return sunGeo;
    }

    //月出月落月相
    public static String generateGetMoonGeo(
            String location,
            String language,
            String unit,
            String start,
            String days
    ) throws Exception {
        String url =  TIANQI_MOON_GEO_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit + "&location=" + location + "&start=" + start + "&days=" + days;
        String moonGeo = Http.get(url).getContent();
        return moonGeo;
    }


    //农历、节气、生肖
    public static String generateGetCalendarLife(
            String location,
            String language,
            String unit,
            String start,
            String days
    ) throws Exception {
        String url =  TIANQI_CALENDAR_LIFE_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit + "&location=" + location + "&start=" + start + "&days=" + days;
        String moonGeo = Http.get(url).getContent();
        return moonGeo;
    }

    //机动车尾号限行
    public static String generateGetDrivingLife(
            String location,
            String language,
            String unit
    ) throws Exception {
        String url =  TIANQI_DRIVING_LIFE_URL + "?"  + "&key=" + TIANQI_API_SECRET_KEY  + "&language=" + language + "&unit=" + unit + "&location=" + location;
        String moonGeo = Http.get(url).getContent();
        return moonGeo;
    }


}