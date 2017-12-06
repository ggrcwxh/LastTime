package com.example.lasttime.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.biz.CallInfoBiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrcwxh on 2017/12/3.
 * 用来申请权限
 */

public class BlankActivity extends AppCompatActivity {
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS
    };
    private SharedPreferences pre;
    private List<String> mPermissionList = new ArrayList<>();
    boolean mShowRequestPermission = true;//用户是否禁止权限
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.blank_layout);
        pre= PreferenceManager.getDefaultSharedPreferences(this);
        boolean alreadyCreate = pre.getBoolean("data_remember",false);
        if(!alreadyCreate){
            SharedPreferences.Editor editor = pre.edit();
            editor.putBoolean("data_remember",true);
            editor.putFloat("call", (float)0.4);
            editor.putFloat("photo",(float)0.35);
            editor.putFloat("word", (float)0.25);
            editor.apply();
        }
        /**
         * 判断哪些权限未授予
         */
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(MyApplication.getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            Intent intent = new Intent(BlankActivity.this,StartActivity.class);
            startActivity(intent);
            finish();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(BlankActivity.this, permissions, 1);
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(BlankActivity.this, permissions[i]);
                        if (showRequestPermission) {
                            Intent intent = new Intent(BlankActivity.this,BlankActivity.class);//重新申请权限
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            mShowRequestPermission = false;//已经禁止
                        }
                    }
                }
                if(mShowRequestPermission){
                    Intent intent = new Intent(BlankActivity.this,StartActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    AlertDialog.Builder dialog= new AlertDialog.Builder(BlankActivity.this);
                    dialog.setTitle("抱歉");
                    dialog.setMessage("没有权限程序无法运行,请在手机设置中打开权限");
                    dialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,int which){
                            finish();
                        }
                    });
                    dialog.show();
                }
                break;
            default:
                break;
        }

    }
}
