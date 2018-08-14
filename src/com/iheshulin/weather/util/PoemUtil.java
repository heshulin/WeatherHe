package com.iheshulin.weather.util;

import org.nutz.dao.util.cri.Static;
import org.nutz.http.Http;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HeShulin on 2018/8/14.
 */
public class PoemUtil {


    public static String convertUTF8ToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        try {
            s = s.toUpperCase();
            int total = s.length() / 2;
            //标识字节长度
            int pos = 0;
            byte[] buffer = new byte[total];
            for (int i = 0; i < total; i++) {
                int start = i * 2;
                //将字符串参数解析为第二个参数指定的基数中的有符号整数。
                buffer[i] = (byte) Integer.parseInt(s.substring(start, start + 2), 16);
                pos++;
            }
            //通过使用指定的字符集解码指定的字节子阵列来构造一个新的字符串。
            //新字符串的长度是字符集的函数，因此可能不等于子数组的长度。
            return new String(buffer, 0, pos, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 将字符串转成unicode
     * @param str 待转字符串
     * @return unicode字符串
     */
    public static String convert(String str)
    {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++)
        {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>>8); //取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    public static String[] sontypelist = new String[]{
            "7yjj", "5yjj"
    };

    //http://tssc.sinaapp.com/index.php/Index/ajax/?callback=jsonp1534208764117&type=ts&sontype=7yjj&kw=%E9%9B%A8&C1=ON
    public static String PlanA(String word) throws UnsupportedEncodingException {
        int randomnum = (int) (0 + Math.random() * (1 - 0 + 1));
        int poemnum = (int) (0 + Math.random() * (1 - 0 + 1));
        String sontype = sontypelist[randomnum];
        String url = new String(("http://tssc.sinaapp.com/index.php/Index/ajax/?callback=jsonp1534208764117&type=ts&sontype=" + sontype + "&kw=" + word + "&C1=ON").getBytes(), "UTF-8");
        String nowAir = Http.get(url).getContent();

        if ("7yjj".equals(sontype)) {
            String poem = unicodeToUtf8(delHTMLTag(nowAir)).substring(50).substring(0, 31);
            String[] poemlist = poem.split("。");
            return poemlist[poemnum];
        } else {
            String poem = unicodeToUtf8(delHTMLTag(nowAir)).substring(50).substring(0, 23);
            String[] poemlist = poem.split("。");
            return poemlist[poemnum];
        }


    }

    public static String[] numtype = new String[]{
            "5", "7"
    };
    public static String PlanB(String word) throws UnsupportedEncodingException {
        int typenum = (int) (1 + Math.random() * (5 - 1 + 1));
        int randomnum = (int) (0 + Math.random() * (1 - 0 + 1));
        int poemnum = (int) (0 + Math.random() * (1 - 0 + 1));
        String url = "http://cts.388g.com/fasong.php?w=" + convert(word).replace("\\","%") + "&num="+numtype[randomnum]+"&type="+typenum+"&yayuntype=1";
        System.out.println("url:" + url);
        String[] poemlist = delHTMLTag(Http.get(url).getContent()).split("。");
        return poemlist[poemnum].replace(" ","").replace("\n","");


    }


    public static String PlanC(String word) throws UnsupportedEncodingException {
        int pagenum = (int) (1 + Math.random() * (10 - 1 + 1));
        String url = "http://www.esk365.com/sccx/scso.php?wd="+word+"&st=0&nd=&tc=1&yb=&page="+pagenum;
        System.out.println("url:" + url);
        String tempstr = Http.get(url).getContent();
//        return delHTMLTag(tempstr.substring(16160).substring(0, 3500));

//        String regex = "<p.*）</p>";
//        tempstr = new String(tempstr.getBytes(),"utf-8");
        System.out.println("1"+tempstr);
        tempstr = tempstr.substring(16160).substring(0, 3500);
        System.out.println("2"+tempstr);
        String regex = "<p class=\"z16\\s*hl32\\s*zq5\">.*?</p>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tempstr);
//        System.out.println("?");
//        while (matcher.find()) {
//
//            System.out.println("==================");
//            System.out.println(matcher.group());
//            System.out.println("==================");
//        }
//        System.out.println("!");

        tempstr = matcher.replaceAll("");
        System.out.println("3"+tempstr);
        tempstr = delHTMLTag(tempstr.replace("&nbsp;","").replace("查询结果",""));
        String regex2 = "（.*?）";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern.matcher(tempstr);
        tempstr = matcher2.replaceAll("").replace("①","").replace("②","").replace("③","").replace("④","").replace("⑤","").replace("⑥","").replace("⑦","").replace("⑧","").replace("⑨","");
        System.out.println("4"+tempstr);
        String[] poemlist = tempstr.split("[？|\\。]");

        int poemnum = (int) (0 + Math.random() * (poemlist.length-2 - 1 + 1));


        System.out.println(poemlist[poemnum]);
        return poemlist[poemnum];
//        return delHTMLTag(sb1);
    }

}
