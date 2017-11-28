package com.example.lasttime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ggrc on 2017/10/27.
 * 用于数据库建表
 */

public class LastTimeDatabaseHelper extends SQLiteOpenHelper {
    //亲友表，用于存储用户设置的亲友号码,以及距离设置的亲友上一次通话的时间
    private static final String CREATE_KITH_AND_KIN_TABLE = "create table KITH_AND_KIN ("
            +"call text primary key, "
            +"num text,"
            +"date integer,"
            +"frequency integer"
            +")";
    //图片表，用于存储用户拍照记录和通过图片记录后获取的信息，尤其是地址信息
    private static final String CREATE_PHOTO_TABLE ="create table PHOTO ("
            +"place text primary key,"
            +"photo_path text, "
            +"date integer,"
            +"frequency integer"
            +")";
    private static final String CREATE_WORD="create table WORD ("
            +"classification text primary key,"
            +"date integer,"
            +"frequency integer"
            +")";
    private static final String CREATE_COMMEMORATION="create table COMMEMORATION ("
            +"data text primary key,"
            +"date integer,"
            +")";
    private static final String CREATE_POINT_IN_TIME_OF_COMMEMORATION="create table TIME ("
            +"time integer primary key,"
            +"frequency integer,"
            +")";
    private static final String CREATE_RIGHT_COMMEMORATION_FREQUENCY="create table RIGHT ("
            +"phone integer,"
            +"address integer,"
            +"app integer,"
            +"behavior integer,"
            +")";
    private static final String CREATE_RECORD_NUMS="create table RECORD ("
            +"kith integer,"
            +"photo integer,"
            +"word integer,"
            +"commemoration integer,"
            +")";
    public LastTimeDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_KITH_AND_KIN_TABLE);//这个表用于存储同步通话记录后的信息
        sqLiteDatabase.execSQL(CREATE_PHOTO_TABLE);//这个表用于储存拍照记录后的信息
        sqLiteDatabase.execSQL(CREATE_WORD);//这个表用于存储手动输入记录后的信息
//        sqLiteDatabase.execSQL(CREATE_COMMEMORATION);//这个表用于存储用户想被记录的信息
  //      sqLiteDatabase.execSQL(CREATE_POINT_IN_TIME_OF_COMMEMORATION);
        //sqLiteDatabase.execSQL(CREATE_RIGHT_COMMEMORATION_FREQUENCY);
        //sqLiteDatabase.execSQL(CREATE_RECORD_NUMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
