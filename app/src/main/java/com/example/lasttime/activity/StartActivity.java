package com.example.lasttime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lasttime.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ggrc on 2017/11/13.
 */

public class StartActivity extends AppCompatActivity {
    private final static int SKIP_TIME=3300;
    private final static int UPADATE_UI_TIME=950;
    private Handler handler1 = new Handler();
    private Handler handler2 = new Handler();
    private TextView text;
    private int count=3;
    private Timer timer=new Timer();
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.start_activity_layout);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        text = (TextView) findViewById(R.id.count_down);
        timer.schedule(task,0,UPADATE_UI_TIME);//动态跟新倒计时TextView
        handler1.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SKIP_TIME);//3秒后跳转至应用主界面MainActivity

    }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        count--;
                        text.setText("倒计时"+count+"s");
                        if(count ==0){
                            timer.cancel();
                        }
                    }
                });
            }
        };
}





