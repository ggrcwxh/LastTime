package com.example.lasttime.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.service.CallInfoService;
import com.example.lasttime.service.IDUDDatebase;
import com.example.lasttime.service.PhotoExifService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ggrc on 2017/10/27.
 */

public class MainActivity extends AppCompatActivity {
    private final int RESULT_CAPTURE_CODE =200;
    public static LastTimeDatabaseHelper dbHelper= new LastTimeDatabaseHelper(MyApplication.getContext(),"lasttime.db",null,1);
    Button forcallinfo;//同步通话记录按钮
    Button forsetkinandkith;//设置亲情账号
    Button forphoto;//通过拍照记录
    Button youlasttime;
    TextView testtext;//用作测试
    String mImagePath;//图片的真实地址
    String mImagePath2;
    @Override
    protected void onCreate(Bundle savedInstaceState){

        super.onCreate(savedInstaceState);
        setContentView(R.layout.main_activity_layout);
        forcallinfo = (Button)findViewById(R.id.forcallinfo);
        forsetkinandkith=(Button)findViewById(R.id.setkinandkith);
        forphoto=(Button)findViewById(R.id.forphoto);
        youlasttime=(Button)findViewById(R.id.you_lasttime);
        testtext=(TextView)findViewById(R.id.test_text);
        CallInfoService callInfoService = new CallInfoService(dbHelper);
        callInfoService.getCallinfos();
        callInfoService.updateKITH_AND_KIN();
        forphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date());//用作拍摄图片的名字
                mImagePath = "/sdcard/" + timeStamp + ".jpg";
                final File tmpCameraFile = new File(mImagePath);
                //通过将给定路径名字符串转换成抽象路径名来创建一个新 File 实例
                //6.0后使用相机需要动态设置权限
                mImagePath2=tmpCameraFile.getPath();
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},222);
                        return;
                    }else{

                        startActivityForResult(new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(tmpCameraFile)),
                                RESULT_CAPTURE_CODE);//打开系统的照相机activity
                    }
                } else {

                    startActivityForResult(new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(tmpCameraFile)),
                            RESULT_CAPTURE_CODE);//打开系统的照相机activity
                }



            }
        });
        forsetkinandkith.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent(MainActivity.this,SetKinAndKithActivity.class);
                startActivity(intent);
            }
        });
        forcallinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDUDDatebase idudDatebase = new IDUDDatebase("KITH_AND_KIN",dbHelper);
                List<CallInfo> list = idudDatebase.selectAll();
                testtext.setText(list.get(0).getCall());
            }
        });
        youlasttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,LogSheetActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        //动态获取读存储器权限
        if(requestCode==RESULT_CAPTURE_CODE&&resultCode==RESULT_OK){
            int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        PERMISSIONS_STORAGE,
                        111
                );
            }
            PhotoExifService photoExifService = new PhotoExifService(mImagePath);
            String temp =photoExifService.getDateLatitudeLongitude();
            testtext.setText(temp);

        }
    }
}