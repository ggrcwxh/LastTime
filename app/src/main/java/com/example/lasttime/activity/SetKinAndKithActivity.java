package com.example.lasttime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lasttime.R;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.service.CallInfoService;
import com.example.lasttime.service.IDUDDatebase;
import com.example.lasttime.service.RecommendService;

/**
 * Created by ggrc on 2017/10/27.
 * 此类是用来设置亲情号码的活动类
 */

public class SetKinAndKithActivity extends AppCompatActivity {
    Button add;
    Button record;
    Button set;
    Button recommend;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.setkinandkith_activity_layout);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        add=(Button)findViewById(R.id.title_add);
        record=(Button)findViewById(R.id.title_record);
        set=(Button)findViewById(R.id.title_set);
        recommend=(Button)findViewById(R.id.title_recommend);
        delete=(Button)findViewById(R.id.setkinandkith_delete);
        final EditText calledittext = (EditText)findViewById(R.id.call_edit_text);
        final EditText numsedittext  =(EditText)findViewById(R.id.num_edit_text);
        Button confirm =(Button)findViewById(R.id.setkinandkith_confirm);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendService recommendService = new RecommendService();
                String temp =recommendService.getRecommend();
                AlertDialog.Builder dialog = new AlertDialog.Builder(SetKinAndKithActivity.this);
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
                Intent intent=new Intent(SetKinAndKithActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SetKinAndKithActivity.this,UserLastTimeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //读取edit上的数据
                String call = calledittext.getText().toString();
                String num  = numsedittext.getText().toString();
                CallInfo callInfo = new CallInfo(call,num);
                IDUDDatebase idudDatebase = new IDUDDatebase("KITH_AND_KIN",callInfo,null,null,MainActivity.dbHelper);
                idudDatebase.insert();
                Toast.makeText(SetKinAndKithActivity.this,"已经存入数据库",Toast.LENGTH_SHORT).show();
              //  CallInfoService callInfoService = new CallInfoService(MainActivity.dbHelper);
               // callInfoService.getCallinfos();
               // callInfoService.updateKITH_AND_KIN();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetKinAndKithActivity.this,DeleteKinAndKithActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}