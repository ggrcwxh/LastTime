package com.example.lasttime.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.lasttime.R;

/**
 * Created by ggrc on 2017/10/30.
 */

public class LogSheetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.logsheet_activity_layout);
        ListView listView = (ListView)findViewById(R.id.log_list_view);

    }
}
