package com.example.lasttime.domain;

/**
 * Created by ggrcwxh on 2017/11/29.
 */

public  abstract  class AbstractInfo {
    protected int frequency;
    public AbstractInfo(int frequency){
        this.frequency=frequency;
    }
    public AbstractInfo(){

    }
    public abstract void setWeight(int weight);
    public abstract int getWeight();
    public abstract long getDate();
    public abstract int getFrequency() ;
    public abstract void setFrequency(int frequency) ;
    public abstract String toString();


}
