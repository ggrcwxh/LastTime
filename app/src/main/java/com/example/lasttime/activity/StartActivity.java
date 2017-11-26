package com.example.lasttime.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.util.CheckInternet;


/**
 * Created by ggrcwxh on 2017/11/13.
 * Stateing it's a non-profit app
 * Checking whether to connect to the Internet
 */

public class StartActivity extends AppCompatActivity {
    private final static int SKIP_TIME=2200;
    private Handler handler1 = new Handler();
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.start_activity_layout);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        if(!CheckInternet.isConnectingToInternet(MyApplication.getContext())){
            Toast.makeText(StartActivity.this,"没有网络哦，部分功能会受限制",Toast.LENGTH_SHORT).show();
        }
        handler1.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SKIP_TIME);//3秒后跳转至应用主界面MainActivity

    }

}





