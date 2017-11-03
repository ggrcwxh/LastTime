package com.example.lasttime.domain;

/**
 * Created by ggrc on 2017/10/27.
 */


public class WordInfo {
        private String classification;
        private long date;
        private long frequency;
        public WordInfo(String classification,long date,long frequency){
            this.classification = classification;
            this.date=date;
            this.frequency=frequency;
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

        public long getFrequency() {
            return frequency;
        }

        public void setFrequency(long frequency) {
            this.frequency = frequency;
        }
    }

