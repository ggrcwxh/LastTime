package com.example.lasttime.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lasttime.MyApplication;
import com.example.lasttime.R;

import com.example.lasttime.biz.CallInfoBiz;
import com.example.lasttime.biz.DatabaseBiz;
import com.example.lasttime.domain.CallInfo;

import java.util.List;

/**
 * Created by ggrcwxh on 2017/11/30.
 */

public class SetKinAndKithActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.set_kin_and_kith_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //从数据库中读取所有联系人的信息
        DatabaseBiz databaseBiz = new DatabaseBiz("KITH_AND_KIN",null,null,null,MainActivity.dbHelper);
        List<CallInfo> list = databaseBiz.selectAllPhone();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.set_kin_and_kith_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DeleteCallAdapter adapter = new DeleteCallAdapter(list);
        recyclerView.setAdapter(adapter);
        Button button = (Button)findViewById(R.id.set_kin_and_kith_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit1 = (EditText)findViewById(R.id.set_kin_and_kith_edit1);
                EditText edit2 = (EditText)findViewById(R.id.set_kin_and_kith_edit2);
                String call = edit1.getText().toString();
                String num = edit2.getText().toString();
                CallInfoBiz callInfoBiz = new CallInfoBiz(MainActivity.dbHelper);
                callInfoBiz.insertKITH_AND_KIN(call,num,0,0);
                Intent intent=new Intent(SetKinAndKithActivity.this,SetKinAndKithActivity.class);
                startActivity(intent);
                finish();

            }
        });
        ImageView imageView = (ImageView)findViewById(R.id.set_kin_and_kith_linkman);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SetKinAndKithActivity.this,LinkmanActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Intent intent = new Intent(SetKinAndKithActivity.this,SetKinAndKithActivity.class);
        startActivity(intent);
        finish();
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
    //内部类，用于适配RecyclerView
    public class DeleteCallAdapter extends RecyclerView.Adapter<DeleteCallAdapter.ViewHolder>{
        private List<CallInfo> list;
        private LocalBroadcastManager localBroadcastManager;
         class ViewHolder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView textView;
             public ViewHolder(View view){
                super(view);
                imageView=(ImageView)view.findViewById(R.id.delete_item_image);
                textView=(TextView)view.findViewById(R.id.delete_item_text);
            }
        }
        public DeleteCallAdapter(List<CallInfo> list){
            this.list=list;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abc_delete_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);
            holder.imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    localBroadcastManager=LocalBroadcastManager.getInstance(MyApplication.getContext());
                    int position=holder.getAdapterPosition();
                    final CallInfo callInfo =list.get(position);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SetKinAndKithActivity.this);
                    dialog.setTitle("提醒");
                    dialog.setMessage("你确定要删除"+callInfo.getCall()+"这个联系人吗？");
                    dialog.setPositiveButton("是的",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,int which){
                            CallInfoBiz callInfoBiz = new CallInfoBiz(MainActivity.dbHelper);
                            callInfoBiz.deleteKITH_AND_KIN(callInfo.getCall(),callInfo.getNum(),callInfo.getDate(),callInfo.getFrequency());
                            Intent intent=new Intent(SetKinAndKithActivity.this,SetKinAndKithActivity.class);
                            startActivity(intent);
                            finish();
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
        public void onBindViewHolder(ViewHolder holder, int position) {
            CallInfo callInfo = list.get(position);
            holder.imageView.setImageResource(R.drawable.delete);
            holder.textView.setText(callInfo.getCall()+":"+callInfo.getNum());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


}
