package com.example.lasttime.service;

import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.domain.PhotoInfo;
import com.example.lasttime.domain.WordInfo;

import java.util.ArrayList;
import java.util.List;

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
        for(CallInfo attribute:callInfos){

        }
        return recommend;
    }

}
