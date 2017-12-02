package com.example.lasttime.biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.domain.CommemorationInfo;
import com.example.lasttime.domain.PhotoInfo;
import com.example.lasttime.domain.WordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrc on 2017/10/27.
 * 此类用于数据库的增删改查
 */

public class DatabaseBiz {
    private LastTimeDatabaseHelper dbHelper;
    private CallInfo callInfo;
    private PhotoInfo photoInfo;
    private WordInfo wordInfo;
    private CommemorationInfo commemorationInfo;
    private String table;//表名称
    public DatabaseBiz(String table , CallInfo callInfo, PhotoInfo photoInfo, WordInfo wordInfo, LastTimeDatabaseHelper dbHelper){
        this.table=table;
        this.callInfo=callInfo;
        this.photoInfo=photoInfo;
        this.wordInfo=wordInfo;
        this.dbHelper=dbHelper;
    }
    public DatabaseBiz(String table, LastTimeDatabaseHelper dbHelper){
        this.table=table;
        this.dbHelper=dbHelper;
    }
    public DatabaseBiz(LastTimeDatabaseHelper dbHelper){
        this.dbHelper=dbHelper;
    }
    //用于数据库的插入操作
    public synchronized void insert(){
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
            ContentValues values = new ContentValues();
            values.put("place",photoInfo.getPlace());
            values.put("photo_path",photoInfo.getPhoto_path());
            values.put("date",photoInfo.getDate());
            values.put("frequency",photoInfo.getFrequency());
            db.insert(table,null,values);
        }
        if(table.equals("WORD")){
            ContentValues values = new ContentValues();
            values.put("classification",wordInfo.getClassification());
            values.put("date",wordInfo.getDate());
            values.put("frequency",wordInfo.getFrequency());
            db.insert(table,null,values);
        }
    }
    //用于更新数据库操作
    public synchronized void update(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(table.equals("KITH_AND_KIN")){
            ContentValues values = new ContentValues();
            values.put("date",callInfo.getDate());
            values.put("frequency",this.selectFrequency()+1);
            db.update("KITH_AND_KIN",values,"num=?",new String[] {callInfo.getNum()});
        }
        if(table.equals("PHOTO")){
            ContentValues values = new ContentValues();
            values.put("date",photoInfo.getDate());
            values.put("frequency",this.selectFrequency()+1);
            db.update(table,values,"place=?",new String[] {photoInfo.getPlace()});
        }
        if(table.equals("WORD")){
            ContentValues values = new ContentValues();
            values.put("classification",wordInfo.getClassification());
            values.put("date",wordInfo.getDate());
            values.put("frequency",selectFrequency()+1);
            db.update(table,values,"classification=?",new String[]{wordInfo.getClassification()});
        }
    }
    //专门用于查询frequency
    public synchronized long selectFrequency() {
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
        if(table.equals("PHOTO")){
            String[] temp={"frequency"};
            String temp1 ="place=?";
            String[] temp2={photoInfo.getPlace()};
            Cursor cursor =db.query(table,temp,temp1,temp2,null,null,null);
            if(cursor!=null&&cursor.moveToFirst()){
                return cursor.getLong(0);
            }
        }
        if(table.equals("WORD")){
            String[] temp={"frequency"};
            String temp1 ="classification=?";
            String[] temp2={wordInfo.getClassification()};
            Cursor cursor = db.query(table,temp,temp1,temp2,null,null,null);
            if(cursor!=null&&cursor.moveToFirst()){
                return cursor.getLong(0);
            }
        }
        return 0;
    }
    //专门用来查询KITH_AND_KIN表中的所有行
    public List<CallInfo> selectAllPhone() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<CallInfo> callInfos = new ArrayList<CallInfo>();
        Cursor cursor = db.query("KITH_AND_KIN", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                CallInfo callInfo = new CallInfo(cursor.getString(0), cursor.getString(1), cursor.getLong(2), (int)cursor.getLong(3));
                callInfos.add(callInfo);

            } while (cursor.moveToNext());

        }
            return callInfos;


    }
    //专门用来查询PHOTO表中所有行
    public synchronized List<PhotoInfo> selectAllPhoto(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<PhotoInfo> photoInfos = new ArrayList<PhotoInfo>();
        Cursor cursor = db.query("PHOTO", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do{
                PhotoInfo photoInfo = new PhotoInfo(cursor.getString(0),cursor.getString(1),cursor.getLong(2),(int)cursor.getLong(3));
                photoInfos.add(photoInfo);
            }while(cursor.moveToNext());

        }
        return photoInfos;
     }


    //专门用来查询WORD表中的所有行
    public synchronized List<WordInfo> selectAllWord() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<WordInfo> wordInfos = new ArrayList<>();
        Cursor cursor = db.query("WORD", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                WordInfo wordInfo = new WordInfo(cursor.getString(0), cursor.getLong(1), (int)cursor.getLong(2));
                wordInfos.add(wordInfo);
            } while (cursor.moveToNext());
        }
        return wordInfos;
    }

    public synchronized void delete(String attribute){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(table.equals("KITH_AND_KIN")){
            db.delete(table,"call=?",new String[] {attribute});
        }
    }
}
