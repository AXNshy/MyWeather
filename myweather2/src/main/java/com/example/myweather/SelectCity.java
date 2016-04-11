package com.example.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by axnshy on 2016/3/26.
 */
public class SelectCity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<String> mList_City=new ArrayList<String>();
    private ListAdapter mAdapter;
    private ListView mListView;
    private EditText mSelectCity;
    private Button mConfirmCity;
    Map<String,String> map;
    Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.citylistview);
        initView();
        initDate();
    }

    private void initView() {

        mSelectCity= (EditText) findViewById(R.id.id_select_city);
        mListView= (ListView) findViewById(R.id.id_list_city);
        mConfirmCity= (Button) findViewById(R.id.id_confirm_city);
        mListView.setOnItemClickListener(this);
        mConfirmCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectCity.getText().toString()!=null&&map.containsKey(mSelectCity.getText().toString())) {
                    Intent intent = new Intent(SelectCity.this, WeatherView.class);

                    intent.putExtra("cityName", mSelectCity.getText().toString());

                    intent.putExtra("cityId", map.get(mSelectCity.getText().toString()));

                    setResult(RESULT_OK,intent);

                     finish();
                }
                else {
                    mSelectCity.setText("请选择城市");
                }
            }
        });
    }


    private void initDate() {
        obtainCityList();
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList_City);
        mListView.setAdapter(mAdapter);

    }


    private void obtainCityList(){

        try {
            InputStream is = this.getAssets().open("city_code.xml");
            map=new XMLParser().getMap(is);
            mList_City=parseMap(map);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    /*
    * 将map.KeySet获得的set集合变成一个ArrayList,
    *
    * */

    private ArrayList<String> parseMap(Map< String,String> map) {
        ArrayList<String> list=new ArrayList<String>();

        if(map !=null && !map.isEmpty()) {
            Set<String> set = map.keySet();
            String[] array = new String[set.size()];
            set.toArray(array);
            for(int i=0;i<array.length;i++){
                list.add(array[i]);
            }
        }
        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectCity=mListView.getItemAtPosition(position)+"";
        mSelectCity.setText(selectCity);
    }
}
