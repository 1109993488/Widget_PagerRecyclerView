package com.blingbling.pagerrecyclerview.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by BlingBling on 2018/7/12.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tv.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView tv;

        public VH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
