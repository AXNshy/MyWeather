package com.shy.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by axnshy on 2016/3/18.
 */
public class CityView extends Activity implements View.OnClickListener {
    //定义整个天气视图
    private RelativeLayout LayoutCityView;
    //定义最上面的城市信息视图
    private LinearLayout LayoutCityInfo;
    //定义中间的城市天气详情视图
    private LinearLayout LayoutWeatherInfo;
    //定义刷新天气ImageButton
    private ImageView refreshImg;
    //定义搜索城市ImageButton
    private ImageView searchImg;
    //定义将城市设为主页ImageButton
    private ImageView btnHomeCity;
    //定义显示城市和天气的数据的TextView
    private TextView textCityName;
    private TextView textPm;
    private TextView textPMLevel;
    private TextView textTemp;
    private TextView textWind;
    private TextView textDate;

    private String currentCityUri;
    //构造函数

    /*
    * 初始化数据
    * */
    public void initData(Weather mWeather){
        if(mWeather!=null){
            this.textCityName.setText(mWeather.getsCityName());
            this.textPm.setText(mWeather.getsPM());
            this.textPMLevel.setText(mWeather.getsPMNum());
            this.textTemp.setText(mWeather.getsTemp());
            this.textWind.setText(mWeather.getsWind());
            this.textDate.setText(mWeather.getsDate());
        }
        else {
            this.textCityName.setText("");
            this.textPm.setText("");
            this.textPMLevel.setText("");
            this.textWind.setText("");
            this.textTemp.setText("网络连接失败！！！");
            this.textDate.setText("");

        }
    }
    /*
    * 设置监听器
    * */
    private void setListener(){
        this.refreshImg.setOnClickListener(this);
    }
    /*
    * 设置当前城市的api接口
    * 使用WeatherAPIHead + CityId + ".html"
    * */
    private String getCityId(){
        String CityId=null;
        Intent intent=getIntent();
        CityId= intent.getStringExtra("cityname");
        return CityId;
    }
    /*
    * 生成一个视图中的全部视图对象并设置父子关系
    * */
    public void initView(Context context){
        this.LayoutCityView=new RelativeLayout(context);
        this.LayoutCityInfo=new LinearLayout(context);
        this.LayoutWeatherInfo=new LinearLayout(context);
        this.refreshImg=new ImageView(context);
        this.searchImg=new ImageView(context);
        this.btnHomeCity=new ImageView(context);
        this.textCityName=new TextView(context);
        this.textPm=new TextView(context);
        this.textPMLevel=new TextView(context);
        this.textTemp=new TextView(context);
        this.textTemp.setText("Temperate");
        this.textDate=new TextView(context);
        this.textWind=new TextView(context);
        this.refreshImg.setImageResource(android.R.drawable.stat_notify_sync_noanim);
        this.searchImg.setImageResource(android.R.drawable.ic_search_category_default);
        this.btnHomeCity.setImageResource(android.R.drawable.btn_default);
        this.refreshImg.setPadding(10,10,10,10);
        this.searchImg.setPadding(10,10,10,10);
        this.LayoutCityView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.LayoutCityInfo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.LayoutWeatherInfo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.refreshImg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.searchImg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.btnHomeCity.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.textCityName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.LayoutCityInfo.addView(this.textCityName);
        this.LayoutCityInfo.addView(this.textPm);
        this.LayoutCityInfo.addView(this.textPMLevel);
        this.LayoutWeatherInfo.addView(this.textTemp);
        this.LayoutWeatherInfo.addView(this.textWind);
        this.LayoutWeatherInfo.addView(this.textDate);
        this.LayoutCityView.addView(this.refreshImg);
        this.LayoutCityView.addView(this.searchImg);
        this.LayoutCityView.addView(this.LayoutCityInfo);
        this.LayoutCityView.addView(this.LayoutWeatherInfo);
        this.LayoutCityView.addView(this.btnHomeCity);
    }
    /*
    * 获得当前页面的最外层容器
    * */
    public  View getLayoutCityView() {
        return this.LayoutCityView;
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
            initData(weather);
        }

    };
/*
* this.refreshImg.getId()=1;
* this.searchImg.getId=2;
* this.btnHomePage=3;
*
*
*
*
*
* */

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 1:this.getWeather();


        }
    }
    Context mContext;
    private void getWeather() {
        /*
        * 新建线程，获取信息
        * */
        if (HttpUtils.isConnection(mContext)) {
            Thread mThread = new Thread(new Runnable() {
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
            });
            mThread.start();
        }
    }
}
