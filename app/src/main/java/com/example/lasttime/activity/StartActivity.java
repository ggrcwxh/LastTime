package com.example.lasttime.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.util.CheckInternet;
import com.example.lasttime.util.CreateFile;

import java.io.File;


/**
 * Created by ggrcwxh on 2017/11/13.
 * Stateing it's a non-profit app
 * Checking whether to connect to the Internet
 */

public class StartActivity extends AppCompatActivity {
    Boolean flag1=false;
    Boolean flag2=false;
    private final static int SKIP_TIME=2200;
    private Handler handler1 = new Handler();
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS
    };
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.start_activity_layout);
        CreateFile.createFile();
        if(!CheckInternet.isConnectingToInternet(MyApplication.getContext())){
            Toast.makeText(StartActivity.this,"没有网络哦，部分功能会受限制",Toast.LENGTH_SHORT).show();
        }
        handler1.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转
            public void run() {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SKIP_TIME);//3秒后跳转至应用主界面MainActivity
        //动态获取存储器权限
        int permission = ActivityCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    StartActivity.this,
                    PERMISSIONS_STORAGE,
                    1
            );

        }

    }

}





