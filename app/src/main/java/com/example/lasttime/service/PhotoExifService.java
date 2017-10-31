package com.example.lasttime.service;

import android.media.ExifInterface;

import com.example.lasttime.domain.PhotoInfo;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        public void getDateLatitudeLongitude()
        {

            try {
                ExifInterface exifInterface = new ExifInterface(path);
                String date=exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                //把String类型变换为Date类型
                String formatType="yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat formatter = new SimpleDateFormat(formatType);
                Date ndate = null;
                try {
                    ndate = formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long nndate;
                if(ndate==null){
                    nndate=0;
                }
                else nndate=ndate.getTime();


                if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                    output1 = convertRationalLatLonToFloat(latValue, latRef);
                    output2 = convertRationalLatLonToFloat(lngValue, lngRef);
                }
                //将经纬度发送到另一个http线程中
                HttpToBaiDuMapService httpToBaiDuMapService = new HttpToBaiDuMapService(output1,output2,nndate);
                new Thread(httpToBaiDuMapService).start();

            } catch (IOException e) {
                e.printStackTrace();
            }


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





}

