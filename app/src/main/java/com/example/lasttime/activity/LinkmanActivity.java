package com.example.lasttime.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lasttime.MyApplication;
import com.example.lasttime.R;
import com.example.lasttime.biz.CallInfoBiz;
import com.example.lasttime.biz.DatabaseBiz;
import com.example.lasttime.domain.CallInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggrcwxh on 2017/12/2.
 */

public class LinkmanActivity extends AppCompatActivity {
    List<CallInfo> callInfos = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.linkman_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        recyclerView=(RecyclerView)findViewById(R.id.linkman_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
            readContacts();
        }
        else{
            readContacts();
        }
    }
    private void readContacts(){
        Cursor cursor =null;
        try{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    CallInfoBiz callInfoBiz = new CallInfoBiz(MainActivity.dbHelper);
                    CallInfo callInfo = callInfoBiz.buildCallInfo(displayName,number,0,0);
                    callInfos.add(callInfo);
                    LinkManAdapter linkManAdapter = new LinkManAdapter(callInfos);
                    recyclerView.setAdapter(linkManAdapter);

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(cursor!=null){
                cursor.close();
            }
        }
    }
    //内部类，用于适配RecyclerView
    public class LinkManAdapter extends RecyclerView.Adapter<LinkmanActivity.LinkManAdapter.ViewHolder>{
        private List<CallInfo> list;
        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView textView;
            public ViewHolder(View view){
                super(view);
                imageView=(ImageView)view.findViewById(R.id.linkman_item_image);
                textView=(TextView)view.findViewById(R.id.linkman_item_text);
            }
        }
        public LinkManAdapter(List<CallInfo> list){
            this.list=list;
        }
        @Override
        public LinkmanActivity.LinkManAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abc_linkman_item,parent,false);
            final LinkmanActivity.LinkManAdapter.ViewHolder holder = new  LinkmanActivity.LinkManAdapter.ViewHolder(view);
            holder.imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position=holder.getAdapterPosition();
                    final CallInfo callInfo =list.get(position);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LinkmanActivity.this);
                    dialog.setTitle("提醒");
                    dialog.setMessage("你确定要加入"+callInfo.getCall()+"这个联系人吗？");
                    dialog.setPositiveButton("是的",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,int which){
                            CallInfoBiz callInfoBiz = new CallInfoBiz(MainActivity.dbHelper);
                            callInfoBiz.insertKITH_AND_KIN(callInfo.getCall(),callInfo.getNum(),callInfo.getDate(),callInfo.getFrequency());
                            Toast.makeText(LinkmanActivity.this, "已经存入数据库啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("放弃",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,int which){

                        }
                    });
                    dialog.show();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(LinkmanActivity.LinkManAdapter.ViewHolder holder, int position) {
            CallInfo callInfo = list.get(position);
            holder.imageView.setImageResource(R.drawable.right);
            holder.textView.setText(callInfo.getCall()+":"+callInfo.getNum());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
