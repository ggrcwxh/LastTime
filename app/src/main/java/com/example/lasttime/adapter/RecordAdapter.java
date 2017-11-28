package com.example.lasttime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ggrcwxh on 2017/11/28.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view){
            super(view);
        }
    }
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
