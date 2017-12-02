package com.example.lasttime.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lasttime.R;
import com.example.lasttime.activity.MainActivity;
import com.example.lasttime.activity.SetKinAndKithActivity;
import com.example.lasttime.biz.CallInfoBiz;
import com.example.lasttime.domain.CallInfo;
import com.example.lasttime.domain.RecordInfo;

import java.util.List;


/**
 * Created by ggrcwxh on 2017/11/30.
 */

public class DeleteCallAdapter extends RecyclerView.Adapter<DeleteCallAdapter.ViewHolder>{
    private List<CallInfo> list;
    static class ViewHolder extends RecyclerView.ViewHolder{
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
                int position=holder.getAdapterPosition();
                CallInfo callInfo =list.get(position);
                CallInfoBiz callInfoBiz = new CallInfoBiz(MainActivity.dbHelper);
                callInfoBiz.deleteKITH_AND_KIN(callInfo.getCall(),callInfo.getNum(),callInfo.getDate(),callInfo.getFrequency());
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
