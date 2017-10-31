package com.example.lasttime.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.domain.PhotoInfo;
import com.example.lasttime.domain.WordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrc on 2017/10/27.
 * 此类用于数据库的增删改查
 */

public class IDUDDatebase {
    private LastTimeDatabaseHelper dbHelper;
    private CallInfo callInfo;
    private PhotoInfo photoInfo;
    private WordInfo wordInfo;
    private String table;//表名称
    public IDUDDatebase(String table ,CallInfo callInfo,PhotoInfo photoInfo,WordInfo wordInfo,LastTimeDatabaseHelper dbHelper){
        this.table=table;
        this.callInfo=callInfo;
        this.photoInfo=photoInfo;
        this.wordInfo=wordInfo;
        this.dbHelper=dbHelper;
    }
    public IDUDDatebase(String table,LastTimeDatabaseHelper dbHelper){
        this.table=table;
        this.dbHelper=dbHelper;
    }
    //用于数据库的插入操作
    public void insert(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(table.equals("KITH_AND_KIN")){
            ContentValues values = new ContentValues();
            values.put("call",callInfo.getCall());
            values.put("num",callInfo.getNum());
            values.put("date",0);
            values.put("frequency",0);
            db.insert(table,null,values);
        }
        if(table.equals("PHOTO")){

        }
        if(table.equals("WORD")){

        }
    }
    //用于更新数据库操作
    public void update(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(table.equals("KITH_AND_KIN")){
            ContentValues values = new ContentValues();
            values.put("date",callInfo.getDate());
            values.put("frequency",this.selectFrequency()+1);
            db.update("KITH_AND_KIN",values,"num=?",new String[] {callInfo.getNum()});
        }
    }
    //专门用于查询frequency
    public long selectFrequency() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(table.equals("KITH_AND_KIN")){
            String[] temp ={"frequency"};
            String temp1 = "num=?";
            String[] temp2 ={callInfo.getNum()};
            Cursor cursor = db.query(table,temp,temp1,temp2,null,null,null);
            if (cursor != null && cursor.moveToFirst())
            {
                return cursor.getLong(0);
            }
        }
        return 0;
    }
    //专门用来查询KITH_AND_KIN表中的所有行
    public List<CallInfo> selectAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (table.equals("KITH_AND_KIN")) {
            List<CallInfo> callInfos = new ArrayList<CallInfo>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CallInfo callInfo = new CallInfo(cursor.getString(0), cursor.getString(1), cursor.getLong(2), cursor.getLong(3));
                    callInfos.add(callInfo);

                } while (cursor.moveToNext());

            }
            return callInfos;

        }
        return null;
    }
    //专门用来查询PHOTO表中所有行
    public List<PhotoInfo> selectAll2(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (table.equals("PHOTO")) {
            List<PhotoInfo> photoInfos = new ArrayList<PhotoInfo>();
            Cursor cursor = db.query(table, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do{
                    PhotoInfo photoInfo = new PhotoInfo(cursor.getString(0),cursor.getString(1),cursor.getLong(2),cursor.getInt(3));
                }while(cursor.moveToNext());

            }
            return photoInfos;
     }

        return null;
    }
}
