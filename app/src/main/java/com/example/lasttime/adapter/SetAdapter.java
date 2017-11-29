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
import com.example.lasttime.activity.SetActivity;
import com.example.lasttime.domain.RecordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67014 on 2017/11/29.
 */

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> implements View.OnClickListener {
    private List<String> list=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener = null;
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        View setView;
        public ViewHolder(View view){
            super(view);
            setView=view;
            textView=(TextView)view.findViewById(R.id.set_item_text);
        }
    }
    public SetAdapter(){
        list.add("设置亲情号码");
        list.add("夜间模式");
        list.add("联系开发者");
        list.add("Github，求watch+star+fork");
        list.add("友情赞助");
    }
    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abc_set_item,parent,false);
        SetAdapter.ViewHolder holder = new SetAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }
    @Override
    public void onBindViewHolder(SetAdapter.ViewHolder holder, int position) {
        String s  = list.get(position);
        holder.textView.setText(s);
        holder.itemView.setTag(position);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
