package com.shy.pageview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class PageView extends AppCompatActivity {
    private LinearLayout mWeatherApp;
    private ViewPager mPageView;
    private PagerAdapter mPageAdapter;
    private List<View> mList=new ArrayList<View>();
    private ImageButton Imagebtn1;
    private ImageButton Imagebtn2;
    private ImageButton Imagebtn3;
    private ImageButton Imagebtn4;
    private ImageButton Imagebtn5;
    private Context mContext;
    public static Integer mWeatherPageNum=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_container);
        initView();
    }
    private void initView(){
        for(int i=0;i<mWeatherPageNum;i++) {
           this.onCreateWeatherPage();
        }
    }
    public void onCreateWeatherPage(){
        mPageView = (ViewPager) findViewById(R.id.id_view_pager);
        Imagebtn1 = (ImageButton) findViewById(R.id.id_tab_host1);
        Imagebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityView aCityView = new CityView(mContext);
                mList.add(aCityView.getLayoutCityView());
                setmPageAdapter();
                mPageView.setAdapter(mPageAdapter);
            }
        });
        setmPageAdapter();
        mPageView.setAdapter(mPageAdapter);
    }
    private void setmPageAdapter(){
        mPageAdapter=new PagerAdapter() {
            //获取当前窗体数
            @Override
            public int getCount() {
                return mList.size();
            }
            //是否由对象生成视图
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
            //从viewgroup中移除当前视图
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mList.get(position));
            }
            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=mList.get(position);
                container.addView(view);
                return view;
            }
        };
    }
}