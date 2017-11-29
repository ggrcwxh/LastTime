package com.example.lasttime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lasttime.R;
import com.example.lasttime.domain.RecordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67014 on 2017/11/29.
 */

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {
    private List<String> list=new ArrayList<>();
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
        holder.setView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(SetAdapter.ViewHolder holder, int position) {
        String s  = list.get(position);
        holder.textView.setText(s);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
