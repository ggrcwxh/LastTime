package com.example.lasttime.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 67014 on 2017/11/1.
 */

public class HttpToServer extends Thread{
    String post;
    String classify;
    public HttpToServer(String post){
        this.post=post;
    }
    @Override
    public void run(){
        String httpPath="101.132.111.29/hanlp";
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
