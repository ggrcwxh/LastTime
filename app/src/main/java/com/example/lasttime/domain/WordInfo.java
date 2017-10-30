package com.example.lasttime.domain;

/**
 * Created by 吴晓晖 on 2017/10/27.
 */


public class WordInfo {
        private String classification;
        private long date;
        private int frequency;

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
    }

