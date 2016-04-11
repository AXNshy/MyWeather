package com.example.myweather;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.R;

import org.w3c.dom.Text;

/**
 * Created by axnshy on 2016/3/18.
 */
public class CityFragment extends android.support.v4.app.Fragment{

    private View view = null;

    //定义显示城市和天气的数据的TextView
    private TextView textCityName ;
    private TextView textPm;
    private TextView textPMLevel;
    private TextView textTemp;
    private TextView textWind;
    private TextView textDate;
    private TextView textError;

    private String currentCityUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home,container,false);
        int i=getArguments().getInt("No");
          if(getArguments().containsKey("cityIdNo"+i)) {
              Log.e("CityFragmentTAG000333","cityIdNo"+i+"="+getArguments().getString("cityIdNo"+i)+"cityNo="+i);
            currentCityUri = Config.WeatherAPIHead + getArguments().getString("cityIdNo"+i) + ".html";
          }else {
            getHomeCityId();
        }
        initView();
        getWeather();
        return view;
    }


    /*
    *
    * 接收到当前要显示天气的城市ID:
    *
    * */
    private String getHomeCityId() {
        String cityId;
        cityId = "101010100";
        return cityId;
    }

    /*
    * 初始化视图,用findviewbyid获取到view
    *
    * */
    public void initView(){
        textCityName= (TextView)view.findViewById(R.id.tv_city_name);
        textError= (TextView) view.findViewById(R.id.tv_error);
        textPm= (TextView) view.findViewById(R.id.tv_pm);
        textPMLevel= (TextView) view.findViewById(R.id.tv_pmlevel);
        textTemp= (TextView) view.findViewById(R.id.tv_temp);
        textWind= (TextView) view.findViewById(R.id.tv_wind);
        textDate= (TextView) view.findViewById(R.id.tv_date);
        textCityName = (TextView)view.findViewById(R.id.tv_city_name);
    }

    /*
    * 显示天气
    * */
    public void displayWeather(Weather mWeather){
        if(mWeather!=null){
            textCityName.setText(mWeather.getsCityName());
            textPm.setText(mWeather.getsPM());
            textPMLevel.setText(mWeather.getsPMLevel());
            textTemp.setText(mWeather.getsTemp());
            textWind.setText(mWeather.getsWind());
            textDate.setText(mWeather.getsDate());
            textError.setText("");
        }
        else {
            textCityName.setText("");
            textPm.setText("");
            textPMLevel.setText("");
            textWind.setText("");
            textTemp.setText("");
            textDate.setText("");

        }
    }
    /*
    * 负责处理从网络上获取信息
    * */
    Handler wHandler=new Handler(){
        /**
         * 处理子线程发送过来的消息
         */
        @Override
        public void handleMessage(Message msg) {
            Weather weather = (Weather) msg.obj;
            //给UI界面上控件赋值
            displayWeather(weather);
        }

    };

    private void setHomeCity() {

        if(textCityName.getText().toString()!=null){


        }
    }


    private void getWeather() {
        Context mContext = this.getActivity();
        /*
        * 新建线程，获取信息
        * */

        if (HttpUtils.isWeatherConnection(mContext)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = HttpUtils.doGet(currentCityUri);
                    Weather mWeather = JsonUtils.parseJson(result);
                    //定义一个Message对象
                    Message msg = Message.obtain();
                    //给Message中的obj属性赋值
                    msg.obj = mWeather;
                    //发送消息给主线程
                    wHandler.sendMessage(msg);
                }
            }).start();
        }
    }
}
