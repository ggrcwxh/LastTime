package com.example.lasttime.biz;

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

public class RecommendBiz {
    List<CallInfo> callInfos = new ArrayList<>();
    List<PhotoInfo> photoInfos = new ArrayList<>();
    List<WordInfo> wordInfos = new ArrayList<>();
    String recommend;
    public RecommendBiz(){
        DatabaseBiz databaseBiz1 = new DatabaseBiz("KITH_AND_KIN",null,null,null,MainActivity.dbHelper);
        DatabaseBiz databaseBiz2 = new DatabaseBiz("PHOTO",null,null,null, MainActivity.dbHelper);
        DatabaseBiz databaseBiz3 = new DatabaseBiz("WORD",null,null,null, MainActivity.dbHelper);
        callInfos = databaseBiz1.selectAllPhone();
        photoInfos = databaseBiz2.selectAllPhoto();
        wordInfos = databaseBiz3.selectAllWord();
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
        if(id!=null){
            switch(id){
                case"call":
                    recommend = String.format("您已距离和%s打电话有%d天了，不如？",callInfo.getCall(),(currentTimeMillis()-callInfo.getDate())/86400000);
                    break;
                case"photo":
                    recommend=String.format("您距离去%s已经有%d天了，不如？",photoInfo.getPlace(),(currentTimeMillis()-photoInfo.getDate())/86400000);
                    break;
                case"word":
                    recommend=String.format("距离%s已经有%d天了，不如？",wordInfo.getClassification(),(currentTimeMillis()-wordInfo.getDate())/86400000);
                    break;
                default:
                    recommend=null;

            }

        }
        else{
            recommend="您还没有记录任何事情哦，不如去设置亲情号码，或者用使用添加内的功能记录:)";
        }

        return recommend;
    }

}
