package com.example.lasttime.service;

import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.domain.PhotoInfo;
import com.example.lasttime.domain.WordInfo;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Created by ggrc on 2017/11/10.
 * 用来向用户推荐一件事
 */

public class RecommendService {
    List<CallInfo> callInfos = new ArrayList<>();
    List<PhotoInfo> photoInfos = new ArrayList<>();
    List<WordInfo> wordInfos = new ArrayList<>();
    String recommend;
    public RecommendService(){
        IDUDDatebase idudDatebase1 = new IDUDDatebase("KITH_AND_KIN",null,null,null,MainActivity.dbHelper);
        IDUDDatebase idudDatebase2 = new IDUDDatebase("PHOTO",null,null,null, MainActivity.dbHelper);
        IDUDDatebase idudDatebase3 = new IDUDDatebase("WORD",null,null,null, MainActivity.dbHelper);
        callInfos = idudDatebase1.selectAll();
        photoInfos = idudDatebase2.selectAll2();
        wordInfos = idudDatebase3.selectAll3();
    }
    public String getRecommend(){
        long max=0;
        String id=null;
        CallInfo callInfo = null;
        PhotoInfo photoInfo = null;
        WordInfo wordInfo = null;
        for(CallInfo attribute:callInfos){
            long temp = (currentTimeMillis()-attribute.getDate())/86400000+attribute.getFrequency();
            if(max<temp){
                max=temp;
                id="call";
                callInfo =attribute;
            }
        }
        for(PhotoInfo attribute:photoInfos) {
            long temp = (currentTimeMillis() - attribute.getDate()) / 86400000 + attribute.getFrequency();
            if (max < temp) {
                max = temp;
                id = "photo";
                photoInfo=attribute;
            }
        }
        for(WordInfo attribute:wordInfos){
            long temp = (currentTimeMillis() - attribute.getDate()) / 86400000 + attribute.getFrequency();
            if (max < temp) {
                max = temp;
                id = "word";
                wordInfo=attribute;
            }
        }
        switch(id){
            case"call":
                recommend = String.format("您已距离和%s打电话有%d天了",callInfo.getCall(),(currentTimeMillis()-callInfo.getDate())/86400000);
                break;
            case"photo":
                recommend=String.format("您距离去%s已经有%d天了",photoInfo.getPlace(),(currentTimeMillis()-photoInfo.getDate())/86400000);
                break;
            case"word":
                recommend=String.format("距离%s已经有%d天了",wordInfo.getClassification(),(currentTimeMillis()-wordInfo.getDate())/86400000);
                break;
            default:
                recommend=null;

        }
        return recommend;
    }

}
