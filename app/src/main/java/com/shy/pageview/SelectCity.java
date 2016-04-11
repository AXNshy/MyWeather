package com.shy.pageview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by axnshy on 2016/3/26.
 */
public class SelectCity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<String> mList_City=new ArrayList<String>();
    private ListAdapter mAdapter;
    private ListView mListView;
    private TextView mSelectCity;
    private Button mConfirmCity;
    Map<String,String> map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citylistview);
        initView();
        initDate();
    }

    private void initView() {
        mSelectCity= (TextView) findViewById(R.id.id_selectcity);
        mListView= (ListView) findViewById(R.id.id_list_city);
        mConfirmCity= (Button) findViewById(R.id.id_confirm_city);
        mListView.setOnItemClickListener(this);
        mConfirmCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SelectCity.this,PageView.class);


                intent.putExtra("cityname",mSelectCity.getText().toString());
                startActivity(intent);

                finish();
            }
        });
    }

    private void initDate() {
        obtainCityList();
        mAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,mList_City);
        mListView.setAdapter(mAdapter);

    }


    private void obtainCityList(){

        try {
            InputStream is = this.getAssets().open("city_code.xml");
            map=new XMLParser().getMap(is);
            mList_City= (List<String>) map.keySet();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectCity=mListView.getItemAtPosition(position)+"";
        mSelectCity.setText(selectCity);
    }
}
