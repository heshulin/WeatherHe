package com.iheshulin.weather.util;


import com.baidu.aip.speech.AipSpeech;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HeShulin on 2017/11/4.
 */
public class Recognition {

    private static final String serverURL = "http://vop.baidu.com/server_api";
    private static String token = "";
    private static  String testFileName = null;
    //put your own params here
    private static final String apiKey = "1vIZVgI10K4xWxWFRxVL7oMR";
    private static final String secretKey = "db3c1aceb1b660bced6edba102a20ab5";
    private static final String cuid = "10322649";
    public static void initMe(String textFileURL){
        testFileName = textFileURL;
    }

    public static void getToken() throws Exception {
        String getTokenURL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
                "&client_id=" + apiKey + "&client_secret=" + secretKey;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
        token = new JSONObject(printResponse(conn)).getString("access_token");
    }


    public static void method1() throws Exception {
        File pcmFile = new File(testFileName);
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();

        // construct params
        JSONObject params = new JSONObject();
        params.put("format", "pcm");
        params.put("rate", 8000);
        params.put("channel", "1");
        params.put("token", token);
        params.put("cuid", cuid);
        params.put("len", pcmFile.length());
        params.put("speech", DatatypeConverter.printBase64Binary(loadFile(pcmFile)));

        // add request header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        // send request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(params.toString());
        wr.flush();
        wr.close();

        printResponse(conn);
    }

    public static void method2() throws Exception {
        File pcmFile = new File(testFileName);
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL
                + "?cuid=" + cuid + "&token=" + token).openConnection();

        // add request header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "audio/pcm; rate=8000");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        // send request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(loadFile(pcmFile));
        wr.flush();
        wr.close();

        printResponse(conn);
    }
    public static final String APP_ID = "10322649";
    public static final String API_KEY = "1vIZVgI10K4xWxWFRxVL7oMR";
    public static final String SECRET_KEY = "db3c1aceb1b660bced6edba102a20ab5";
    public static String method3(String File) throws Exception {


        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        JSONObject res = client.asr(File, "wav", 16000, null);
        System.out.println(res.toString());
        String tempString = res.get("result").toString().replaceAll("\"","").replaceAll("，","").substring(1,res.get("result").toString().length()-4);
        System.out.println(res.get("result").toString().replaceAll("\"","").replaceAll("，","").substring(1,res.get("result").toString().length()-4));
        System.out.println("识别文字为"+tempString);
//        AudioInterceptUtils.convertAudioFiles("E:\\hackx\\16k.wav","E:\\hackx\\16k.pcm");
        return tempString;
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
        System.out.println(new JSONObject(response.toString()).toString(4));
        return response.toString();
    }

    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
