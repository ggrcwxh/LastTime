package com.example.lasttime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lasttime.R;
import com.example.lasttime.adapter.DeleteCallAdapter;
import com.example.lasttime.biz.CallInfoBiz;
import com.example.lasttime.biz.DatabaseBiz;
import com.example.lasttime.domain.CallInfo;

import java.util.List;

/**
 * Created by ggrcwxh on 2017/11/30.
 */

public class SetKinAndKithActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.set_kin_and_kith_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //从数据库中读取所有联系人的信息
        DatabaseBiz databaseBiz = new DatabaseBiz("KITH_AND_KIN",null,null,null,MainActivity.dbHelper);
        List<CallInfo> list = databaseBiz.selectAllPhone();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.set_kin_and_kith_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DeleteCallAdapter adapter = new DeleteCallAdapter(list);
        recyclerView.setAdapter(adapter);
        Button button = (Button)findViewById(R.id.set_kin_and_kith_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit1 = (EditText)findViewById(R.id.set_kin_and_kith_edit1);
                EditText edit2 = (EditText)findViewById(R.id.set_kin_and_kith_edit2);
                String call = edit1.getText().toString();
                String num = edit2.getText().toString();
                CallInfoBiz callInfoBiz = new CallInfoBiz(MainActivity.dbHelper);
                callInfoBiz.insertKITH_AND_KIN(call,num,0,0);
                Intent intent=new Intent(SetKinAndKithActivity.this,SetKinAndKithActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
