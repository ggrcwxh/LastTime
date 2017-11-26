package com.example.lasttime.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.biz.CallInfoBiz;
import com.example.lasttime.thread.HttpToServer;
import com.example.lasttime.biz.PhotoExifBiz;
import com.example.lasttime.biz.RecommendBiz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ggrc on 2017/10/27.
 */

public class MainActivity extends AppCompatActivity {
    private final int RESULT_CAPTURE_CODE =200;
    private  final int RESULT_IMAGE_CODE = 100;
    public static LastTimeDatabaseHelper dbHelper= new LastTimeDatabaseHelper(MyApplication.getContext(),"lasttime.db",null,1);//创建数据库
    Button camera;//通过拍照记录
    Button album;
    Button add;
    Button record;
    Button set;
    Button confirm;
    Button recommend;
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
        album=(Button)findViewById(R.id.main_album);
        add=(Button)findViewById(R.id.title_add);
        record=(Button)findViewById(R.id.title_record);
        set=(Button)findViewById(R.id.title_set);
        edit=(EditText)findViewById(R.id.main_edit);
        confirm=(Button)findViewById(R.id.main_confirm);
        recommend=(Button)findViewById(R.id.title_recommend);
        //执行读取通话记录，更新数据库
        CallInfoBiz callInfoBiz = new CallInfoBiz(dbHelper);
        callInfoBiz.getCallInfos();
        callInfoBiz.updateKITH_AND_KIN();
        //以下title栏的按钮点击事件
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendBiz recommendBiz = new RecommendBiz();
                String temp = recommendBiz.getRecommend();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("推荐");
                dialog.setMessage(temp);
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定",null);
                dialog.show();
            }
        });
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
                Intent intent =new Intent(MainActivity.this,SetActivity.class);
                startActivity(intent);

            }
        });
        //点击拍照按钮的事件
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
        //点击相册按钮事件
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date());
                mImagePath = "/sdcard/" + timeStamp + ".jpg";
                final File tmpCameraFile = new File(mImagePath);
                String[] PERMISSIONS_STORAGE = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                //动态获取存储器权限
                int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                PERMISSIONS_STORAGE,
                                111
                        );
                }
                startActivityForResult(
                        new Intent(Intent.ACTION_PICK).setType(
                                "image/*").putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(tmpCameraFile)),
                        RESULT_IMAGE_CODE);






            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post=edit.getText().toString();
                if(!post.equals("")){
                    HttpToServer httpToServer = new HttpToServer(post);
                    httpToServer.start();
                    Toast.makeText(MainActivity.this,"已经帮您将相关信息存入数据库，可以在记录中查看啦",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"您并没有输入相关数据",Toast.LENGTH_SHORT).show();

                }

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
        //动态获取存储器权限
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
            PhotoExifBiz photoExifBiz = new PhotoExifBiz(mImagePath);
            Boolean flag= photoExifBiz.getDateLatitudeLongitude();
            if(flag){
                Toast.makeText(MainActivity.this,"已经帮您将相关信息存入数据库,可以在记录中查看啦",Toast.LENGTH_SHORT).show();
            }
            else{
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("抱歉");
                dialog.setMessage("无法读到你的图片的地址信息，如果是使用拍照功能的话请打开gps");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定",null);
                dialog.show();
            }

        }
        if(requestCode==RESULT_IMAGE_CODE&&resultCode==RESULT_OK){
            Uri uri =data.getData();
            final String scheme = uri.getScheme();
            String mImagePath = null;
            if ( scheme == null )
                mImagePath = uri.getPath();
            else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
                mImagePath = uri.getPath();
            } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
                Cursor cursor = MyApplication.getContext().getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
                if ( null != cursor ) {
                    if ( cursor.moveToFirst() ) {
                        int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                        if ( index > -1 ) {
                            mImagePath = cursor.getString( index );
                        }
                    }
                    cursor.close();
                }
            }

            PhotoExifBiz photoExifBiz = new PhotoExifBiz(mImagePath);
            Boolean flag= photoExifBiz.getDateLatitudeLongitude();
            if(flag){
                Toast.makeText(MainActivity.this,"已经帮您将相关信息存入数据库,可以在记录中查看啦",Toast.LENGTH_SHORT).show();
            }
            else{
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("抱歉");
                dialog.setMessage("无法读到你的图片的地址信息，如果是使用拍照功能的话请打开gps");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定",null);
                dialog.show();
            }
        }
    }
}