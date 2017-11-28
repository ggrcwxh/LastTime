package com.example.lasttime.domain;

/**
 * Created by ggrcwxh on 2017/11/28.
 */

public class RecordInfo {
    private String record;
    private long lastTime;
    public RecordInfo(String record,long lastTime){
        this.record=record;
        this.lastTime=lastTime;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
}
