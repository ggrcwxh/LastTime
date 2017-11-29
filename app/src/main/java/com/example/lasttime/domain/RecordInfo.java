package com.example.lasttime.domain;

/**
 * Created by ggrcwxh on 2017/11/28.
 */

public class RecordInfo {
    private String record;
    private int imageId;
    public RecordInfo(String record,int imageId){
        this.record=record;
        this.imageId=imageId;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
