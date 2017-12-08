package com.example.lasttime.activity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lasttime.R;

import java.util.Date;

/**
 * Created by 67014 on 2017/12/8.
 */

public class WriteRecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.abc_input_record);
        TextView textView = (TextView)findViewById(R.id.input_record_text);
        String formatType="yyyy:MM:dd HH:mm";
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(formatType);
        Date date =new Date();
        textView.setText(simpleDateFormat.format(date));
    }
}
