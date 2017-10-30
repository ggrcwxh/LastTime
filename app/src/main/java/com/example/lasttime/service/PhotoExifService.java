package com.example.lasttime.service;

import android.media.ExifInterface;

import com.example.lasttime.domain.PhotoInfo;

import java.io.IOException;

/**
 * Created by 吴晓晖 on 2017/10/27.
 * 此类用于读取照片中的exif信息，是PhotoInfo的行为类
 */

public class PhotoExifService {

        private String path;
        private PhotoInfo photoInfo = new PhotoInfo();
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

                return date+" "+latValue+" "+latRef+" "+lngValue+" "+lngRef;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }



    }

