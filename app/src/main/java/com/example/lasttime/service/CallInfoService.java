package com.example.lasttime.service;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.MyApplication;
import com.example.lasttime.domain.CallInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrc on 2017/10/27.
 * 此类用于读取通话信息以及将信息筛选存入数据库，是CallInfo行为类
 */

public class CallInfoService {
    private List<CallInfo> callinfos = new ArrayList<CallInfo>();//用List来存取读到的通话记录
    private LastTimeDatabaseHelper dbHelper;
    public CallInfoService(LastTimeDatabaseHelper dbHelper){
        this.dbHelper=dbHelper;
    }
    public void getCallInfos() {

        ContentResolver resolver = MyApplication.getContext().getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.TYPE
        };
        if (ActivityCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return ;
        }
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        while (cursor.moveToNext()){
            if(cursor.getInt(2)==2)//OUTGOING_TYPE=2
            {
                String number=cursor.getString(0);
                long date = cursor.getLong(1);
                boolean flag=false;
                //for循环用于判断当前读取到的号码是否在callinfo中有相同号码；如果有相同号码，当读取到的时间更接近现在时更新
                for(CallInfo attribute:callinfos)
                {

                    if(attribute.getNum().equals(number)) {
                        if(attribute.getDate()<date)
                        {
                            attribute.setDate(date);
                            flag=true;
                            break;
                        }
                    }


                }
                //如果没有相同的号码则直接插入callinfos
                if(flag==false)
                {
                    CallInfo temp = new CallInfo(number,date);
                    callinfos.add(temp);
                }


            }
        }
        cursor.close();


    }
    //调用IDUDDatebase中的方法更新数据库
    public void updateKITH_AND_KIN(){
        IDUDDatebase idudDatebase = new IDUDDatebase("KITH_AND_KIN",dbHelper);
        List<CallInfo> callinfos2 = idudDatebase.selectAll();
        if(callinfos2==null)return;
        for(CallInfo attribute: this.callinfos){
            for(CallInfo attribute2: callinfos2){
                if(attribute.getNum().equals(attribute2.getNum())&&attribute.getDate()>attribute2.getDate()){
                    IDUDDatebase idudDatebase2 = new IDUDDatebase("KITH_AND_KIN",attribute,null,null,dbHelper);
                    idudDatebase2.update();
                }
            }
        }
    }
    public List<CallInfo> getCallinfos() {
        return callinfos;
    }
}
