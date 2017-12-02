package com.example.lasttime.biz;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telecom.Call;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.MyApplication;
import com.example.lasttime.domain.CallInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrc on 2017/10/27.
 * 此类用于读取通话信息以及将信息筛选存入数据库，是CallInfo行为类
 */

public class CallInfoBiz {
    private List<CallInfo> callinfos = new ArrayList<CallInfo>();//用List来存取读到的通话记录
    private LastTimeDatabaseHelper dbHelper;
    public CallInfoBiz(LastTimeDatabaseHelper dbHelper){
        this.dbHelper=dbHelper;
    }
    public CallInfo buildCallInfo(String call,String num,long date,int frequency){
         return new CallInfo(call,num,date,frequency);
    }
    public List<CallInfo> getCallinfos() {
        getCallInfosInPhone();
        return callinfos;
    }
    public void getCallInfosInPhone() {
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
        DatabaseBiz databaseBiz = new DatabaseBiz("KITH_AND_KIN",dbHelper);
        List<CallInfo> callinfos2 = databaseBiz.selectAllPhone();
        if(callinfos2.size()==0)return;
        for(CallInfo attribute: this.callinfos){
            for(CallInfo attribute2: callinfos2){
                if(attribute.getNum().equals(attribute2.getNum())&&attribute.getDate()>attribute2.getDate()){
                    DatabaseBiz databaseBiz2 = new DatabaseBiz("KITH_AND_KIN",attribute,null,null,dbHelper);
                    databaseBiz2.update();
                }
            }
        }
    }
    public void insertKITH_AND_KIN(String call,String num,long date,int frequency){
        getCallInfosInPhone();
        CallInfo callInfo = buildCallInfo(call,num,date,frequency);
        DatabaseBiz databaseBiz = new DatabaseBiz("KITH_AND_KIN",callInfo,null,null,dbHelper);
        List<CallInfo> callinfos2 =databaseBiz.selectAllPhone();
        if(callinfos2.size()==0){
            databaseBiz.insert();
        }
        else{
            for(CallInfo attribute:callinfos2){
                attribute.getCall().equals(callInfo.getCall());
                return;
            }
            databaseBiz.insert();
        }
    }

}
