package com.shy.pageview;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by axnshy on 2016/3/14.
 */
public class JsonUtils {

    /**
     * 把Json数据转化成一个Weather类对象
     */

    public static  Weather parseJson(String jsonString) {
        if(jsonString == null) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject1 = jsonObject.getJSONObject("weatherinfo");
            //城市名
            String cityName  = jsonObject1.getString("city");
            //城市Id
            String cityId=jsonObject1.getString("cityid");
            //PM值
            String pm = jsonObject1.getString("pm")+"  "+jsonObject1.getString("pm-level");
            //PM级别
            String pmNum = jsonObject1.getString("pm-num");
            //温度
            String temp = jsonObject1.getString("temp") + "℃";
            //天气
            String weather = jsonObject1.getString("weather1") +""+ jsonObject1.getString("temp1");
            //风
            String wind = jsonObject1.getString("wd") + " " +jsonObject1.getString("ws");
            //日期
            String date = jsonObject1.getString("date_y") + "  " + jsonObject1.getString("week");

            Weather  mWeather = new Weather(cityName,cityId, pm, pmNum, temp, weather, wind, date);

            return mWeather;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
