package com.example.lasttime.domain;

/**
 * Created by ggrc on 2017/10/27.
 * 此类是用户通话信息数据类
 */

public class CallInfo {
    private String call;
    private String num;
    private long date;
    private long frequency;
    public  CallInfo(String num,long date){
        this.num=num;
        this.date=date;
    }
    public CallInfo(String call,String num){
        this.call=call;
        this.num=num;
    }
    public CallInfo(String call,String num,long date,long frequency){
        this.call=call;
        this.num=num;
        this.date=date;
        this.frequency=frequency;
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

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
}
