package com.example.myweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.StaleDataException;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by axnshy on 16/4/1.
 */
public class SettingFile {
    //设置共享偏好
    private static SharedPreferences setting;
    private SQLiteDatabase weatherDB;
    private Cursor cursor;
    private String[] wCityName;
    private String[] wCityId;
    /*
    * 判断WeatherDB数据库中的city表中有没有数据
    *
    * */
    public boolean isWeatherDataExisted = false;


    /*
    * Constructor
    * */
    public SettingFile(Context context) {
        setting = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        openOrCreateWeatherDB(context);
        createCityTable(context);
        getCityIdName();
        closeDataBase();
    }

    private void closeDataBase() {
        weatherDB.close();
    }

    /*
    * 判断是否是首次打开APP
    *
    * */

    public static boolean isAppOpenedFirst(Context context) {
        setting = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (setting.getInt("nowCityNum",0)>0) {
            return false;
        } else
            return true;
    }
    /*
    * get the CityNum in SharedPreference
    *
    * */
     public  static  void getCityNum(Context context){
         SharedPreferences setting = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
         Config.totalCityNum = setting.getInt("nowCityNum", 0);

     }

    public static void addCityNum(Context context) {
        SharedPreferences setting = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        Config.totalCityNum= setting.getInt("nowCityNum", 0);
        Config.totalCityNum++;
        editor.putInt("nowCityNum", Config.totalCityNum);
        editor.commit();
    }

    public static void addCityIdName(Context context,String cityId,String cityName){
        SharedPreferences setting = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=setting.edit();
        editor.putString("cityIdNo"+Config.totalCityNum,cityId);
        editor.putString("cityNameNo"+Config.totalCityNum,cityName);
        editor.commit();
    }
    public void  setHome(Context context,String name)
    {
        setting=context.getSharedPreferences("settings",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=setting.edit();

        editor.putString("home",name);

    }
    //设置定义当前的背景颜色
    public void getSettingBackground(String background){
        setting.getString("background",background);
        Log.i("TAG00777","设置背景颜色成功");
    }
    //获取到当前设置的背景颜色,若没获取到就默认为蓝色
    public String getSettingCityId(){
        return setting.getString("background","#0000FF");
    }

    public  void openOrCreateWeatherDB(Context context){
        try {
            weatherDB=context.openOrCreateDatabase("weatherDB", Context.MODE_PRIVATE, new SQLiteDatabase.CursorFactory() {
                @Override
                public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery,
                                        String editTable, SQLiteQuery query) {
                    return null;
                }
            });
        }catch (SQLiteException p){
            p.printStackTrace();
        }
    }
    //创建数据表city
    private  void createCityTable(Context context){
        String createCityTable="CREATE TABLE IF NOT EXISTS city (_id INTEGER PRIMARY KEY AUTOINCREMENT,cityName varchar,cityId varchar,pm varchar,pmLevel varchar,temperature varchar,wind varchar,weather varchar,date varchar)";
        weatherDB.execSQL(createCityTable);
    }
    //插入天气数据
    public void insertWeatherDate(Weather weather) {
        weatherDB.execSQL("INSERT INTO city VALUES (cityName,cityId, pm, pmLevel, temperature, wind, weather, date)", new Object[]{ weather.getsCityName(),weather.getsCityId(), weather.getsPM(), weather.getsPMLevel(), weather.getsTemp(), weather.getsWind(), weather.getsWeather(), weather.getsDate()});

    }

    private Cursor getCursor(){
        String cityidname[]={"cityName","cityId"};
        this.cursor=weatherDB.rawQuery("select cityName,cityId from city",null);
        return cursor;
    }


    public void getCityIdName(){

        this.getCursor();
        if(this.cursor.isNull(cursor.getColumnIndex("cityId"))) {
            this.cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {
                this.wCityId[i] = cursor.getString(cursor.getColumnIndex("cityId"));
                this.wCityName[i] = cursor.getString(cursor.getColumnIndex("cityName"));
                i++;
                isWeatherDataExisted = true;
            }
        }else
            isWeatherDataExisted=false;

    }


    /*
    * 获取SharedPreference中存放的第i个城市的id
    * */

    public static String getCityNameNoi(Context context,int i){
        setting=context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        if(Config.totalCityNum>0) {
            return setting.getString("cityNameNo" + i, null);
        }
        return null;
    }
    public static String getCityIdNoi(Context context,int i) {
        setting=context.getSharedPreferences("settings",Context.MODE_PRIVATE);
        if(Config.totalCityNum>0) {
            return setting.getString("cityIdNo" + i, null);
        }
        Log.e("Weather TAG000888","Config.totalCityNum=  小于0或为空   ");
        return null;
    }
}

