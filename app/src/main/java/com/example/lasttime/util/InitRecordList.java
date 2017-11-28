package com.example.lasttime.util;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.domain.RecordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrcwxh on 2017/11/28.
 */

public class InitRecordList {
    private static List<RecordInfo> list = new ArrayList<>();
    public static List<RecordInfo> initRecordList(){
        LastTimeDatabaseHelper lastTimeDatabaseHelper = new LastTimeDatabaseHelper(MainActivity.dbHelper)
        return list;
    }
}
