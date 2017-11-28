package com.example.lasttime.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by ggrcwxh on 2017/11/27.
 */

public class CreateFile {
    public static final void createFile(){
        File file = new File("/sdcard/LastTime/picture/");
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
