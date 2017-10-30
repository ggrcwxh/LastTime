package com.example.lasttime.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lasttime.R;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.service.CallInfoService;
import com.example.lasttime.service.IDUDDatebase;

import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by ggrc on 2017/10/30.
 */

public class LogSheetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.logsheet_activity_layout);
        CallInfoService callInfoService = new CallInfoService(MainActivity.dbHelper);
        callInfoService.getCallinfos();
        callInfoService.updateKITH_AND_KIN();
        ListView listView = (ListView)findViewById(R.id.log_list_view);
        IDUDDatebase idudDatebase1=new IDUDDatebase("KITH_AND_KIN",MainActivity.dbHelper);
        List<CallInfo> list1 = idudDatebase1.selectAll();
        String[] data =new String[50];
        int i=0;
        for(CallInfo attribute:list1){
            data[i++]=String.format("您已距离和%s打电话有%d天了",attribute.getCall(),(currentTimeMillis()-attribute.getDate())/86400000);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(LogSheetActivity.this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);

    }
}
