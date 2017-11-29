package com.example.lasttime.domain;

import static java.lang.System.currentTimeMillis;

/**
 * Created by ggrc on 2017/10/27.
 * 此类是用户通话信息数据类
 */

public class CallInfo extends AbstractInfo {
    private String call;
    private String num;
    private long date;

    public  CallInfo(String num,long date){
        this.num=num;
        this.date=date;
    }
    public CallInfo(String call,String num){
        this.call=call;
        this.num=num;
    }
    public CallInfo(String call,String num,long date,int frequency){
        super(frequency);
        this.call=call;
        this.num=num;
        this.date=date;

    }
    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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
        return String.format("离上一次: 离call%s已经有%d天了",call,day);
    }
}
