package com.example.myweather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by axnshy on 2016/3/26.
 */
public class HttpUtils {
    /*
    * 判断网络是否正常
    * */

    public static boolean isWeatherConnection(Context context){


        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if (info==null){
            return false;
        }
        else {
            return true;
        }
    }
    /*
    * 使用get的方式访问网络并获取到Json数据
    *
    * */
    public static String doGet(String uriString){
        StringBuffer sb=new StringBuffer();
        String result=null;
        try {
            URL url=new URL(uriString);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(10*1000);

            conn.setReadTimeout(10*1000);

            InputStream is=conn.getInputStream();

            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            String line=br.readLine();

            if (line!=null){

                sb.append(line);
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(sb.toString().contains("weather_callback")){
            result=sb.toString().substring(sb.toString().indexOf("(")+1,sb.toString().indexOf(")"));
        }
        return result;
    }
}
