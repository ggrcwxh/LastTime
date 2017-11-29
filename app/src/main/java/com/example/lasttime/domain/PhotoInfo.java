package com.example.lasttime.domain;

import static java.lang.System.currentTimeMillis;

/**
 * Created by ggrc on 2017/10/27.
 * 此类是图片信息数据类
 */

public class PhotoInfo extends AbstractInfo {
    private String place;
    private String photo_path;
    private long date;

    public PhotoInfo(String place,String photo_path,long date,int frequency){
          super(frequency);
          this.place=place;
          this.photo_path=photo_path;
          this.date=date;

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

    public String toString(){
        long day= (currentTimeMillis()-getDate())/86400000;
        return String.format("离上一次:去%s已经有%d天了",place,day);
    }
}
