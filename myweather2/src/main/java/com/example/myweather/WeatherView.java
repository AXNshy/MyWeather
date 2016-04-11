package com.example.myweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.R;
import com.example.myweather.activitylist.Setting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by axnshy on 16/4/6.
 */
public class WeatherView extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mWeatherApp;
    private ViewPager mPageView;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private ImageButton Imagebtn1;
    private ImageButton Imagebtn2;
    private ImageButton Imagebtn3;
    private ImageButton Imagebtn4;
    private ImageButton Imagebtn5;
    private Context mContext;
    private SettingFile setting;
    public static Integer mWeatherPageNum = 0;
    Bundle mBundle = new Bundle();

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initView();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOverflowButtonAlways();
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.container);
        initView();
        initEvent();
        if (SettingFile.isAppOpenedFirst(mContext)) {
            Intent intent = new Intent();
            intent.setClass(mContext, SelectCity.class);
            startActivityForResult(intent, Config.add);
        } else {

        }
        Log.e("weather TAG000555","**************************************Config.totalCityNum=="+Config.totalCityNum+"cityId"+SettingFile.getCityIdNoi(mContext,Config.totalCityNum));
        //initWeatherData();
    }

    /*
    * set Listener for all tab imagebutton
    *
    * */
    private void initEvent() {
        Imagebtn1.setOnClickListener(this);
        Imagebtn2.setOnClickListener(this);
        Imagebtn3.setOnClickListener(this);
        Imagebtn4.setOnClickListener(this);
        Imagebtn5.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_weather:

        }
    }

    private void setOverflowButtonAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKey = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 获取menu并填充menu
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int view_id = item.getItemId();
        switch (view_id) {
            case R.id.id_action_search:{
                break;
            }
            case R.id.id_menu_add: {
                Intent intent = new Intent();
                intent.setClass(mContext, SelectCity.class);
                startActivityForResult(intent, Config.add);
                break;
            }
            case R.id.id_menu_login: {
                break;
            }
            case R.id.id_menu_setting: {
                break;
            }

        }
        return true;
    }

    private void initWeatherData() {
        setting = new SettingFile(this);
    }

    private void initView() {
        Imagebtn1 = (ImageButton) findViewById(R.id.id_iv_pic);
        Imagebtn2 = (ImageButton) findViewById(R.id.id_iv_edit);
        Imagebtn3 = (ImageButton) findViewById(R.id.id_iv_weather);
        Imagebtn4 = (ImageButton) findViewById(R.id.id_iv_setting);
        Imagebtn5 = (ImageButton) findViewById(R.id.id_iv_user);
        initAllTabImg();
        Imagebtn3.setImageResource(R.drawable.iconmonstr_weather_32);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        //android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        SettingFile.getCityNum(mContext);
        //初始化ViewPager
        mPageView = (ViewPager) findViewById(R.id.id_container);
        if(Config.totalCityNum>0) {
            for (int i = 1; i <= Config.totalCityNum; i++) {
                Bundle bundle = new Bundle();
                String cityName = SettingFile.getCityNameNoi(mContext, i);
                String cityId = SettingFile.getCityIdNoi(mContext, i);
                Fragment fragment = new CityFragment();
                bundle.putInt("No", i);
                bundle.putString("cityNameNo" + i, cityName);
                bundle.putString("cityIdNo" + i, cityId);
                Log.e("TAG000111", "cityNameNo" + i + "=" + cityName + "cityIdNo" + i + "=" + cityId);
                fragment.setArguments(bundle);
                mList.add(fragment);
                mPageView.setAdapter(mAdapter);
            }
        }
        mAdapter = new FragmentPagerAdapter(manager) {

            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };
        mPageView.setAdapter(mAdapter);
    }

    /*
    * 接受从SelectCity Activity中传递过来的intent.
    *
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.add&&resultCode==RESULT_OK) {
            Bundle bundle=new Bundle();
            String cityName = data.getStringExtra("cityName");
            String cityId = data.getStringExtra("cityId");
            SettingFile.addCityNum(mContext);
            SettingFile.addCityIdName(mContext,cityId,cityName);

            Log.e("TAG000222","cityName="+cityName+"cityId="+cityId+"nowCityNum="+Config.totalCityNum);
            bundle.putInt("No",Config.totalCityNum);
            bundle.putString("cityNameNo"+Config.totalCityNum, cityName);
            bundle.putString("cityIdNo"+Config.totalCityNum, cityId);
            CityFragment mFragment = new CityFragment();
            mFragment.setArguments(bundle);
            mList.add(mFragment);
            mPageView.setAdapter(mAdapter);
            initAllTabImg();
            Imagebtn3.setImageResource(R.drawable.iconmonstr_weather_32);
        }
    }

    /*
    * 将所有的ImageView设为初始颜色大小
    * */
    private void initAllTabImg() {
        Imagebtn1.setImageResource(R.drawable.iconmonstr_picture_24_light);
        Imagebtn2.setImageResource(R.drawable.iconmonstr_edit_24_light);
        Imagebtn3.setImageResource(R.drawable.iconmonstr_weather_24_light);
        Imagebtn4.setImageResource(R.drawable.iconmonstr_gear_24_light);
        Imagebtn5.setImageResource(R.drawable.iconmonstr_user_24_light);

    }

}