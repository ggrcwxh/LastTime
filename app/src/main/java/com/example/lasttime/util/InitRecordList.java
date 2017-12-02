package com.example.lasttime.util;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.R;
import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.biz.DatabaseBiz;
import com.example.lasttime.domain.AbstractInfo;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.domain.PhotoInfo;
import com.example.lasttime.domain.RecordInfo;
import com.example.lasttime.domain.WordInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ggrcwxh on 2017/11/28.
 */

public class InitRecordList {
    private static List<RecordInfo> list = new ArrayList<>();
    private static List<AbstractInfo> infos =new ArrayList<>();
    public static List<RecordInfo> initRecordList(){
        list.clear();
        infos.clear();
        DatabaseBiz databaseBiz  = new DatabaseBiz(MainActivity.dbHelper);
        List<CallInfo> callInfos = databaseBiz.selectAllPhone();
        List<PhotoInfo> photoInfos = databaseBiz.selectAllPhoto();
        List<WordInfo> wordInfos = databaseBiz.selectAllWord();
        infos.addAll(callInfos);
        infos.addAll(photoInfos);
        infos.addAll(wordInfos);
        /*
         *因为记录的条数大概率小于286条，参考DualPivotQuicksort源码采用插入排序
         *降序排列objectList
         */
        for(int i=0,j=i;i<infos.size()-1;j=++i){
            AbstractInfo info = infos.get(i+1);
            while(info.getFrequency()>infos.get(j).getFrequency()){
                infos.set(j+1,infos.get(j));
                if(j--==0)break;

            }
            infos.set(j+1,info);

        }
        //构造迭代器
        Iterator<AbstractInfo> it = infos.iterator();
        while(it.hasNext()){
            AbstractInfo info =it.next();
            //如果date是0的话不显示
            if(info.getDate()==0){
                it.remove();
                continue;
            }
            //RTTI
            String s = info.getClass().getName();
            if(s.equals("com.example.lasttime.domain.CallInfo")){
                list.add(new RecordInfo(info.toString(),R.drawable.phone));
            }
            if(s.equals("com.example.lasttime.domain.PhotoInfo")){
                list.add(new RecordInfo(info.toString(),R.drawable.pic));
            }
            if(s.equals("com.example.lasttime.domain.WordInfo")){
                list.add(new RecordInfo(info.toString(),R.drawable.pen));
            }
        }


        return list;
    }
}
