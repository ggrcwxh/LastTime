package com.example.lasttime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lasttime.R;
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
        ViewHolder holder = new ViewHolder(view);
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
