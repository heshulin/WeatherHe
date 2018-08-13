package com.iheshulin.weather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HeShulin on 2017/5/30.
 */
public class Crawler {
    //OKhttp对象
    private final OkHttpClient client = new OkHttpClient();



    //存放返回值路径
    private String photourl="";
    //存放posturl
    private String url="http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=detail&fr=&sf=1&fmq=1447473655189_R&pv=&ic=0&nc=1&z=&se=&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=";
    public void pullPhoto(String photoname) throws IOException {
        url=url+photoname;
//        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String html=response.body().string();

//        System.out.println(html);

        String regEx="\"objURL\":\"(.*?)\"";
//        System.out.print(regEx);
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            photourl=matcher.group();
            photourl=photourl.replace("\"objURL\":\"","");
            photourl=photourl.replace("\"","");
            System.out.print(photourl);
        }

    }
    public String getPhotourl() {
        return photourl;
    }
}
