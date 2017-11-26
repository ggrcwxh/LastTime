package com.example.lasttime.thread;

import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.domain.WordInfo;
import com.example.lasttime.biz.DatabaseBiz;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by 67014 on 2017/11/1.
 * 此类用于和服务器交互来获取用户手写输入分类
 * 这个类暂时被放弃但是为了避免日后需要使用，依然保留在代码中
 */

public class HttpToServer extends Thread{
    String post;
    String classify;
    long date;
    WordInfo wordInfo;
    public HttpToServer(String post){
        this.post=post;
    }
    @Override
    public void run(){
        String httpPath="http://101.132.111.29/hanlp";
        URL url = null;
        try {
            url = new URL(httpPath);
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.write(post);
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            classify=bos.toString("utf-8");
            date=System.currentTimeMillis();
            wordInfo=new WordInfo(classify,date,0);
            updateToDatabase();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void updateToDatabase(){
        DatabaseBiz databaseBiz = new DatabaseBiz("WORD",null,null,wordInfo, MainActivity.dbHelper);
        List<WordInfo> list = databaseBiz.selectAll3();
        boolean flag = false;
        for(WordInfo attribute : list){
            if(wordInfo.getClassification().equals(attribute.getClassification())){
                databaseBiz.update();
                flag=true;
            }
        }
        if(flag==false){
            databaseBiz.insert();
        }
    }
}
