package com.example.lasttime.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * Created by ggrc on 2017/10/31.
 */

public class HttpToBaiDuMapService implements Runnable {
    float output1;
    float output2;
    long date;
    public HttpToBaiDuMapService(float output1,float output2,long date){
        this.output1=output1;
        this.output2=output2;
        this.date=date;
    }
    @Override
    public void run(){
        String httpPath="http://api.map.baidu.com/cloudrgc/v1?location="+output1+","+output2+"&geotable_id=135675&coord_type=bd09ll&ak=E9ZYWuO733BgH6Epr8ZqjvG2Ai5L4l7n";
        URL url = null;
        try {
            url = new URL(httpPath);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            connection.disconnect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder reponse = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                reponse.append(line);
            }
            String s=reponse.toString();
            parseJSONWithJSONObject(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void parseJSONWithJSONObject(String s){
        String formatted_address;
        String recommended_location_description;
        String address;
        try {
            JSONObject jsonObject = new JSONObject(s);
            formatted_address = jsonObject.getString("formatted_address");
            recommended_location_description = jsonObject.getString("recommended_location_description");
            JSONObject jsonObject1 = jsonObject.getJSONObject("address_component");
            if((address=jsonObject1.getString("street"))==null){
                address= jsonObject.getString("district");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
