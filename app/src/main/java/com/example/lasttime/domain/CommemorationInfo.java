package com.example.lasttime.domain;

/**
 * Created by 67014 on 2017/11/13.
 */

public class CommemorationInfo {
    private String data;
    private long date;
    public CommemorationInfo(String data,long date){
        this.data=data;
        this.date=date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
