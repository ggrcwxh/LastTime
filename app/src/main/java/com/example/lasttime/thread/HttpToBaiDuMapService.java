package com.example.lasttime.thread;


import android.support.v7.app.AppCompatActivity;


import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.domain.PhotoInfo;
import com.example.lasttime.util.DatabaseBiz;

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
import java.util.List;
import java.util.concurrent.Callable;


/**
 * Created by ggrc on 2017/10/31.
 * 此类用于使用百度lps云服务进行反地理编码
 * 同时还要对得到的json信息进行处理以及数据库的存储
 */

public class HttpToBaiDuMapService extends AppCompatActivity implements Callable<Boolean> {
    float output1;
    float output2;
    long date;
    PhotoInfo photoInfo;
    String photoPath;
    public HttpToBaiDuMapService(float output1,float output2,long date,String photoPath){
        this.output1=output1;
        this.output2=output2;
        this.date=date;
        this.photoPath= photoPath;
    }
    @Override
    public Boolean call(){
        String httpPath="http://api.map.baidu.com/cloudrgc/v1?location="+output1+","+output2+"&geotable_id=135675&coord_type=bd09ll&ak=E9ZYWuO733BgH6Epr8ZqjvG2Ai5L4l7n";
        URL url = null;
        Boolean flag=false;
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
            flag=parseJSONWithJSONObject(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;

    }
    private Boolean parseJSONWithJSONObject(String s){
        String formatted_address;
        String recommended_location_description;
        Boolean flag = false;
        String address;
        try {
            JSONObject jsonObject = new JSONObject(s);
            //formatted_address = jsonObject.getString("formatted_address");
            recommended_location_description = jsonObject.getString("recommended_location_description");
            JSONObject jsonObject1 = jsonObject.getJSONObject("address_component");
            if((address=jsonObject1.getString("street"))==null){
                address= jsonObject.getString("district");
            }
            String[] temp = recommended_location_description.split("东|南|西|北|中|内");
            StringBuffer data=new StringBuffer();
            for(int i=0;i<temp.length-1;i++){
                data.append(temp[i]);

            }
            String data1=data.toString();
            flag=updateToDatabase(data1);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flag;


    }
    private Boolean updateToDatabase(String s){
        if(s.equals("")){

            return false;
        }
        photoInfo = new PhotoInfo(s,photoPath,date,0);
        DatabaseBiz databaseBiz =new DatabaseBiz("PHOTO",null,photoInfo,null, MainActivity.dbHelper);
        List<PhotoInfo> list= databaseBiz.selectAll2();
        boolean flag = false;
        for(PhotoInfo attribute: list){
            if(attribute.getPlace().equals(photoInfo.getPlace())){
                databaseBiz.update();
                flag=true;
            }
        }
        if(flag==false){
            databaseBiz.insert();
        }
        return true;

    }
}
