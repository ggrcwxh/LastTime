package com.example.lasttime.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.lasttime.R;

/**
 * Created by ggrc on 2017/11/11.
 */

public class BlankActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.main_activity_layout);
        //关掉标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(BlankActivity.this);
        dialog.setTitle("推荐");
        dialog.setMessage("诶呀，图片无法读取地址信息；如果是拍照的话请打开GPS，不如就是百度地图的锅啦:)");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog ,int which){
                finish();
            }

        });
        dialog.show();
    }
}
