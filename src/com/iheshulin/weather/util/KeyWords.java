package com.iheshulin.weather.util;

import com.show.api.ShowApiRequest;

/**
 * Created by HeShulin on 2017/11/4.
 */
public class KeyWords {
    public static String getKeyWords(String text){
        String res=new ShowApiRequest("http://route.showapi.com/920-1","49285","eb271b7a50bc4bf09dcc566017b8f407")
                .addTextPara("title",text)
                .addTextPara("content",text)
                .addTextPara("num","1")
                .post();
        return(res);
    }
}
