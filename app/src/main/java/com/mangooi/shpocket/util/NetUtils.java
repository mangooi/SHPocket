package com.mangooi.shpocket.util;

import android.util.Log;

import com.mangooi.shpocket.data.Constant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/21.
 */

public class NetUtils {
    public static String request(String httpUrl, String httpArg){
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        sbf.append('[');
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", Constant.BAIDU_API_KEY);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            sbf.append(']');
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("NetUtils","失败");
        }
        return result;
    }
}
