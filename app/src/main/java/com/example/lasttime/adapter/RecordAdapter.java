package com.example.lasttime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lasttime.R;
import com.example.lasttime.domain.RecordInfo;

import java.util.List;

/**
 * Created by ggrcwxh on 2017/11/28.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private List<RecordInfo> recordInfos;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view){
            super(view);
        }
    }
    public RecordAdapter(List<RecordInfo> recordInfos){
        this.recordInfos=recordInfos;
    }
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder holder, int position) {
        RecordInfo recordInfo = recordInfos.get(position);
        holder.imageView.setImageResource(recordInfo.getImageId());
        holder.textView.setText(recordInfo.toString());

    }

    @Override
    public int getItemCount() {
        return recordInfos.size();
    }
}
