package com.example.lasttime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lasttime.R;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.biz.DatabaseBiz;
import com.example.lasttime.biz.RecommendBiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrc on 2017/11/10.
 * 此类是用来删除亲情号码的活动类
 */

public class DeleteKinAndKithActivity extends AppCompatActivity {
    private DatabaseBiz databaseBiz;
    Button add;
    Button record;
    Button set;
    Button recommend;
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.deletekinandkith_activity_layout);
        //关掉标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        add=(Button)findViewById(R.id.title_add);
        record=(Button)findViewById(R.id.title_record);
        set=(Button)findViewById(R.id.title_set);
        recommend=(Button)findViewById(R.id.title_recommend);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendBiz recommendBiz = new RecommendBiz();
                String temp = recommendBiz.getRecommend();
                AlertDialog.Builder dialog = new AlertDialog.Builder(DeleteKinAndKithActivity.this);
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
                Intent intent=new Intent(DeleteKinAndKithActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DeleteKinAndKithActivity.this,UserLastTimeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteKinAndKithActivity.this,SetActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ListView listView = (ListView)findViewById(R.id.delete_listview);
        //从数据库中读取所有联系人的信息
        databaseBiz = new DatabaseBiz("KITH_AND_KIN",null,null,null,MainActivity.dbHelper);
        List<CallInfo> list = databaseBiz.selectAllPhone();
        List<String> list2 = new ArrayList<>();
        List<String> list3 =new ArrayList<>();
        for(CallInfo attribute:list){
            list2.add(String.format("称呼:%s,电话:%s",attribute.getCall(),attribute.getNum()));
            list3.add(attribute.getCall());
        }
        final List<String> list4=list3;
        //动态生成ListView数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteKinAndKithActivity.this,android.R.layout.simple_list_item_1,list2);
        listView.setAdapter(adapter);
        //ListView的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String s =list4.get(position);
                databaseBiz.delete(s);
                Toast.makeText(DeleteKinAndKithActivity.this,"已经从数据库删除啦",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
