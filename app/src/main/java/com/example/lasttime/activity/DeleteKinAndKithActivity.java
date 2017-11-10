package com.example.lasttime.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lasttime.R;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.service.IDUDDatebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrc on 2017/11/10.
 */

public class DeleteKinAndKithActivity extends AppCompatActivity {
    private IDUDDatebase idudDatebase;
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.deletekinandkith_activity_layout);
        //关掉标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        final ListView listView = (ListView)findViewById(R.id.delete_listview);
        //从数据库中读取所有联系人的信息
        idudDatebase = new IDUDDatebase("KITH_AND_KIN",null,null,null,MainActivity.dbHelper);
        List<CallInfo> list = idudDatebase.selectAll();
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
                idudDatebase.delete(s);
                Toast.makeText(DeleteKinAndKithActivity.this,"已经从数据库删除啦",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
