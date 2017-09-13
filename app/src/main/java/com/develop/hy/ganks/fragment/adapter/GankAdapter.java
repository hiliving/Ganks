package com.develop.hy.ganks.fragment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.ui.WebViewActivity;
import com.just.library.AgentWeb;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Helloworld on 2017/9/13.
 */

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.GanKHolder> {

    private Context context;
    private List<GankBean.ResultsBean> resultsBeen;
    public GankAdapter(Context context, List<GankBean.ResultsBean> results) {
        this.context = context;
        this.resultsBeen = results;
    }

    @Override
    public GanKHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new GanKHolder(view);
    }

    @Override
    public void onBindViewHolder(GanKHolder holder, final int position) {
            holder.tv_title.setTag(resultsBeen.get(position));
            holder.tv_title.setText(resultsBeen.get(position).getDesc());
            holder.tv_content.setText("来源："+resultsBeen.get(position).getWho());
            holder.carview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onItemClick(position);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return resultsBeen.size();
    }

    class GanKHolder extends RecyclerView.ViewHolder{
         @BindView(R.id.tv_list_item_title)
        TextView tv_title;
        @BindView(R.id.tv_list_item_content)
        TextView tv_content;
        @BindView(R.id.carview)
        CardView carview;


        public GanKHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setOnItemtClickListener(OnItemClickListener listener){
        this.listener= listener;
    }
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
