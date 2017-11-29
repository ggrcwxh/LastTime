package com.example.lasttime.domain;

import static java.lang.System.currentTimeMillis;

/**
 * Created by ggrc on 2017/10/27.
 * 此类是用户手写输入数据类
 */


public class WordInfo extends AbstractInfo {
        private String classification;
        private long date;

        public WordInfo(String classification,long date,int frequency){
            super(frequency);
            this.classification = classification;
            this.date=date;
        }
        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
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
        return String.format("离上一次:%s已经有%d天了",classification,day);
    }
    }

