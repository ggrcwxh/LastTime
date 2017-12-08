package com.example.lasttime.biz;

import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.domain.WordInfo;

import java.util.Date;

/**
 * Created by 67014 on 2017/12/8.
 */

public class WordInfoBiz {
    int year,month,day;
    String word;
    public WordInfoBiz(int year,int month,int day,String word){
        this.year=year;
        this.month=month;
        this.day=day;
        this.word=word;
    }
    public void insertToDatabase(){
        Date date = new Date(year,month,day);
        long dateLong ;
        dateLong=date.getTime();
        WordInfo wordInfo = buildWordInfo(word,dateLong,0);
        DatabaseBiz databaseBiz = new DatabaseBiz("WORD",null,null,wordInfo, MainActivity.dbHelper);
        databaseBiz.insert();
    }
    public WordInfo buildWordInfo(String word,long date,int frequency){
        WordInfo wordInfo = new WordInfo(word,date,frequency);
        return wordInfo;
    }
}
