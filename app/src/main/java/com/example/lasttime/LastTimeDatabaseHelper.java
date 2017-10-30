package com.example.lasttime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 67014 on 2017/10/27.
 */

public class LastTimeDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_KITH_AND_KIN = "create table KITH_AND_KIN ("
            + "call text primary key, "
            +"num text,"
            +"date integer,"
            +"frequency integer"
            +")";
    public static final String CREATE_PHOTO="create table PHOTO ("
            +"place text primary key,"
            +"photo_path text, "
            +"date integer,"
            +"frequency integer"
            +")";
    public static final String CREATE_WORD="create table WORD ("
            +"classification text primary key,"
            +"date integer,"
            +"frequency integer"
            +")";
    public LastTimeDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_KITH_AND_KIN);//这个表用于存储同步通话记录后的信息
        sqLiteDatabase.execSQL(CREATE_PHOTO);//这个表用于储存拍照记录后的信息
        sqLiteDatabase.execSQL(CREATE_WORD);//这个表用于手动输入记录后的信息
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
