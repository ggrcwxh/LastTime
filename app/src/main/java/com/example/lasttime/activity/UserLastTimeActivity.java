package com.example.lasttime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.lasttime.R;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.domain.PhotoInfo;
import com.example.lasttime.domain.WordInfo;
import com.example.lasttime.service.CallInfoService;
import com.example.lasttime.service.IDUDDatabase;
import com.example.lasttime.service.RecommendService;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by ggrc on 2017/10/31.
 * 此类是用来显示用户上一次时间的活动类
 */

public class UserLastTimeActivity extends AppCompatActivity {
    Button add;
    Button record;
    Button set;
    Button recommend;
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.user_lasttime_activity);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        CallInfoService callInfoService0 = new CallInfoService(MainActivity.dbHelper);
        callInfoService0.getCallInfos();
        callInfoService0.updateKITH_AND_KIN();
        ListView listView = (ListView)findViewById(R.id.user_last_time);
        add=(Button)findViewById(R.id.title_add);
        record=(Button)findViewById(R.id.title_record);
        set=(Button)findViewById(R.id.title_set);
        recommend=(Button)findViewById(R.id.title_recommend);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendService recommendService = new RecommendService();
                String temp =recommendService.getRecommend();
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserLastTimeActivity.this);
                dialog.setTitle("推荐");
                dialog.setMessage(temp);
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定",null);
                dialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserLastTimeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLastTimeActivity.this,SetActivity.class);
                startActivity(intent);
                finish();
            }
        });
        CallInfoService callInfoService = new CallInfoService(MainActivity.dbHelper);
        callInfoService.getCallinfos();
        callInfoService.updateKITH_AND_KIN();
        IDUDDatabase idudDatabase1 =new IDUDDatabase("KITH_AND_KIN",MainActivity.dbHelper);
        IDUDDatabase idudDatabase2 =new IDUDDatabase("PHOTO",MainActivity.dbHelper);
        IDUDDatabase idudDatabase3 =new IDUDDatabase("WORD",MainActivity.dbHelper);
        List<CallInfo> list1 = idudDatabase1.selectAll();
        List<PhotoInfo> list2 = idudDatabase2.selectAll2();
        List<WordInfo> list3 = idudDatabase3.selectAll3();
        List<String> data =new ArrayList<>();
        if(list1.size()!=0){
            for(CallInfo attribute:list1){
                data.add(String.format("您已距离和%s打电话有%d天了",attribute.getCall(),(currentTimeMillis()-attribute.getDate())/86400000));
            }
        }
        if(list2.size()!=0){
            for(PhotoInfo attribute:list2){
                data.add(String.format("您距离去%s已经有%d天了",attribute.getPlace(),(currentTimeMillis()-attribute.getDate())/86400000));
            }
        }
        if(list3.size()!=0){
            for(WordInfo attribute:list3){
                data.add(String.format("距离%s已经有%d天了",attribute.getClassification(),(currentTimeMillis()-attribute.getDate())/86400000));
            }
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(UserLastTimeActivity.this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);



    }
}
