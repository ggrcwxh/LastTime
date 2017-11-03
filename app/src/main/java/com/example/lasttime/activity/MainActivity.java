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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.service.CallInfoService;
import com.example.lasttime.service.HttpToServer;
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
    public static LastTimeDatabaseHelper dbHelper= new LastTimeDatabaseHelper(MyApplication.getContext(),"lasttime.db",null,1);//创建数据库
    Button camera;//通过拍照记录
    Button add;
    Button record;
    Button set;
    Button confirm;
    EditText edit;
    String mImagePath;//图片的真实地址
    String mImagePath2;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        //动态获取通话记录权限
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 333);
            }
        }
        setContentView(R.layout.main_activity_layout);
        //关掉标题栏
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        camera=(Button)findViewById(R.id.main_camera);
        add=(Button)findViewById(R.id.title_add);
        record=(Button)findViewById(R.id.title_record);
        set=(Button)findViewById(R.id.title_set);
        edit=(EditText)findViewById(R.id.main_edit);
        confirm=(Button)findViewById(R.id.main_confirm);
        //执行读取通话记录，更新数据库
        CallInfoService callInfoService = new CallInfoService(dbHelper);
        callInfoService.getCallInfos();
        callInfoService.updateKITH_AND_KIN();
        //点击拍照按钮的事件
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,UserLastTimeActivity.class);
                startActivity(intent);
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SetKinAndKithActivity.class);
                startActivity(intent);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date());//用作拍摄图片的名字
                mImagePath = "/sdcard/" + timeStamp + ".jpg";
                final File tmpCameraFile = new File(mImagePath);
                //通过将给定路径名字符串转换成抽象路径名来创建一个新 File 实例
                //6.0后使用相机需要动态设置权限
                mImagePath2=tmpCameraFile.getPath();
                //动态获取打开相机权限
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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post=edit.getText().toString();
                HttpToServer httpToServer = new HttpToServer(post);
                httpToServer.start();
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
            //将照片地址送入读取exif相关类交给后台处理
            PhotoExifService photoExifService = new PhotoExifService(mImagePath);
            photoExifService.getDateLatitudeLongitude();
            Toast.makeText(MainActivity.this,"已经帮您将相关信息存入数据库",Toast.LENGTH_SHORT).show();

        }
    }
}