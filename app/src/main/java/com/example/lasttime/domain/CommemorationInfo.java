package com.example.lasttime.domain;

/**
 * Created by 67014 on 2017/11/13.
 */

public class CommemorationInfo {
    private String data;
    private int date;

    public CommemorationInfo(String data,int date){
        this.data=data;
        this.date=date;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
