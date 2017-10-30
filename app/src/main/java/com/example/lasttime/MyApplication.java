package com.example.lasttime;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by 67014 on 2017/10/27.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        context=getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }
}