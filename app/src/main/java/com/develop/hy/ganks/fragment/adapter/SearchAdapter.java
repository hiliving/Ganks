package com.develop.hy.ganks.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.hy.ganks.R;

/**
 * Created by Helloworld on 2017/9/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {
    private String arr[] = {"高仿","妹子","开源框架","下拉刷新","热更新"};

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(arr[position]);
    }


    @Override
    public int getItemCount() {
        return arr.length;
    }
    class MyHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
