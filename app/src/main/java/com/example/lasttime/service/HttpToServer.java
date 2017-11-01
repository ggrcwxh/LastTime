package com.example.lasttime.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 67014 on 2017/11/1.
 */

public class HttpToServer extends Thread{
    @Override
    public void run(){
        String httpPath="";
        URL url = null;
        try {
            url = new URL(httpPath);
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
