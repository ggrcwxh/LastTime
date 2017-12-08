package com.example.lasttime.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lasttime.LastTimeDatabaseHelper;
import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.adapter.RecordAdapter;
import com.example.lasttime.biz.CallInfoBiz;
import com.example.lasttime.domain.RecordInfo;
import com.example.lasttime.thread.HttpToServer;
import com.example.lasttime.biz.PhotoExifBiz;
import com.example.lasttime.biz.RecommendBiz;
import com.example.lasttime.util.InitRecordList;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ggrcwxh on 2017/10/27.
 *
 */

public class MainActivity extends AppCompatActivity {
    //一些参数

    private final int RESULT_CAPTURE_CODE = 200;
    private final int RESULT_IMAGE_CODE = 100;
    private final int OPEN_CAMERA = 222;
    private final int GET_USER_CALL_INFO = 333;
    private final int WRITE_USER_EXTERNAL_STORAGE = 111;
    //数据库
    public static LastTimeDatabaseHelper dbHelper = new LastTimeDatabaseHelper(MyApplication.getContext(), "lasttime.db", null, 1);
    //图片地址
    String mImagePath;
    RecyclerView recyclerView;
    //记录
    private List<RecordInfo> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.main_activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button home = (Button)findViewById(R.id.bottom_home);
        Button set =(Button)findViewById(R.id.bottom_set);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        //动态获取通话记录权限
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG);
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, GET_USER_CALL_INFO);
        }
        else{
            //执行读取通话记录，更新数据库
            CallInfoBiz callInfoBiz = new CallInfoBiz(dbHelper);
            callInfoBiz.getCallInfosInPhone();
            callInfoBiz.updateKITH_AND_KIN();

        }
        List<RecordInfo> recordInfoList= InitRecordList.initRecordList();
        recyclerView =(RecyclerView)findViewById(R.id.main_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecordAdapter adapter = new RecordAdapter(recordInfoList);
        recyclerView.setAdapter(adapter);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SetActivity.class);

                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("测试");
                dialog.setMessage("测试，无信息");
                dialog.setPositiveButton("这是我想要的",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){

                    }
                });
                dialog.setNegativeButton("这不是我想要的",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){

                    }
                });
                dialog.show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                break;
            case R.id.write:
                writeRecord();
                break;
            case R.id.camera:
                useCameraRecord();
                break;
            case R.id.album:
                albumRecord();
                break;
        }
        return true;
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void writeRecord(){
        Intent intent = new Intent(MainActivity.this,WriteRecordActivity.class);
        startActivity(intent);

    }
    //使用相机记录
    private final void useCameraRecord() {
        //创造图片文件的文件名
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        //文件物理地址
        mImagePath = "/sdcard/LastTime/picture/" + timeStamp + ".jpg";
        //通过将给定路径名字符串转换成抽象路径名来创建一个新 File 实例
        final File tmpCameraFile = new File(mImagePath);
        //动态获取相机权限
        boolean flag = false;
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        //为了防止手机在等待用户授权的同时继续并发执行下一步操作，造成程序崩溃，所以有两个打开相机语句
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    OPEN_CAMERA);
        }
        else{
            //打开照相机
            startActivityForResult(new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(tmpCameraFile)),
                    RESULT_CAPTURE_CODE);
        }

    }
    //使用相册选择照片记录
    private final void albumRecord() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        mImagePath = "/sdcard/LastTime/picture/" + timeStamp + ".jpg";
        final File tmpCameraFile = new File(mImagePath);
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        //动态获取存储器权限
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    PERMISSIONS_STORAGE,
                    WRITE_USER_EXTERNAL_STORAGE
            );

        }
        //打开相册
        else{
            startActivityForResult(
                    new Intent(Intent.ACTION_PICK).setType(
                            "image/*").putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(tmpCameraFile)),
                    RESULT_IMAGE_CODE);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        //如果是相机记录
        if (requestCode == RESULT_CAPTURE_CODE && resultCode == RESULT_OK) {
            //动态获取存储器权限
            int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        PERMISSIONS_STORAGE,
                        WRITE_USER_EXTERNAL_STORAGE
                );

            }
            //将照片地址送入读取exif相关类交给后台处理
            else{
                PhotoExifBiz photoExifBiz = new PhotoExifBiz(mImagePath);
                Boolean flag = photoExifBiz.getDateLatitudeLongitude();
                if (flag) {
                    Toast.makeText(MainActivity.this, "已经帮您将相关信息存入数据库,可以在记录中查看啦", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("抱歉");
                    dialog.setMessage("无法读到你的图片的地址信息，如果是使用拍照功能的话请打开gps");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", null);
                    dialog.show();
                }

            }

        }
        //如果是照片记录
        if (requestCode == RESULT_IMAGE_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String scheme = uri.getScheme();
            String mImagePath = null;
            //抽象路径转换为具体路径
            if (scheme == null)
                mImagePath = uri.getPath();
            else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                mImagePath = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = MyApplication.getContext().getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            mImagePath = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
            PhotoExifBiz photoExifBiz = new PhotoExifBiz(mImagePath);
            Boolean flag = photoExifBiz.getDateLatitudeLongitude();
            if (flag) {
                Toast.makeText(MainActivity.this, "已经帮您将相关信息存入数据库,可以在记录中查看啦", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("抱歉");
                dialog.setMessage("无法读到你的图片的地址信息，如果是使用拍照功能的话请打开gps");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", null);
                dialog.show();
            }
        }
    }








}