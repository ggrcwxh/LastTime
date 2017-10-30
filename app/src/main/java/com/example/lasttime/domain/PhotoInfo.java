package com.example.lasttime.domain;

/**
 * Created by ggrc on 2017/10/27.
 * 此类是图片信息数据类
 */

public class PhotoInfo {
    private String place;
    private String photo_path;
    private long date;
    private int frequency;
    public PhotoInfo(String place,String photo_path,long date,int frequency){

    }
    public PhotoInfo(){

    }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
