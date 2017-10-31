package com.example.lasttime.service;

import android.media.ExifInterface;

import com.example.lasttime.domain.PhotoInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ggrc on 2017/10/27.
 * 此类用于读取照片中的exif信息，是PhotoInfo的行为类
 */

public class PhotoExifService {

        private String path;
        private PhotoInfo photoInfo = new PhotoInfo();
        private float output1 = 0;
        private float output2 = 0;
        public PhotoExifService(String path)
        {
            this.path=path;
        }
        public String getDateLatitudeLongitude()
        {

            try {
                ExifInterface exifInterface = new ExifInterface(path);
                String date=exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

                if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                    output1 = convertRationalLatLonToFloat(latValue, latRef);
                    output2 = convertRationalLatLonToFloat(lngValue, lngRef);
                }
                //向api发送get方法
                String httpPath=String.format("http://api.map.baidu.com/cloudrgc/v1?location=%d,%d&geotable_id=135675&coord_type=bd09ll&ak=E9ZYWuO733BgH6Epr8ZqjvG2Ai5L4l7n"
                ,output1,output2);
                URL url = new URL(httpPath);
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
                return reponse.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
        private static float convertRationalLatLonToFloat(String rationalString, String ref) {
            String[] parts = rationalString.split(",");
            String[] pair;
            pair = parts[0].split("/");
            double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());
            pair = parts[1].split("/");
            double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());
            pair = parts[2].split("/");
            double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());
            double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
            if ((ref.equals("S") || ref.equals("W"))) {
                return (float) -result;
            }
            return (float) result;
    }

    public void ReverseAddress(){

        }



    }

