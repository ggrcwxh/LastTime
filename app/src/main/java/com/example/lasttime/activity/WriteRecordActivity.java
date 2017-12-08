package com.example.lasttime.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lasttime.R;
import com.example.lasttime.biz.WordInfoBiz;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 67014 on 2017/12/8.
 */

public class WriteRecordActivity extends AppCompatActivity {
    private int year,month,day;
    private Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.abc_input_record);
        final TextView textView = (TextView)findViewById(R.id.input_record_text);
        textView.setText(year+":"+month+":"+day);
        getDate();
        Button confirm = (Button)findViewById(R.id.input_record_confirm);
        Button back=(Button)findViewById(R.id.input_record_back);
        Button set = (Button)findViewById(R.id.input_record_set_time);
        final EditText editText=(EditText)findViewById(R.id.input_record_edit);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                WordInfoBiz wordInfoBiz = new WordInfoBiz(year,month,day,s);
                wordInfoBiz.insertToDatabase();
                Intent intent =new Intent(WriteRecordActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        textView.setText(year+":"+month+":"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(WriteRecordActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
            }
        });

    }
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }

}
