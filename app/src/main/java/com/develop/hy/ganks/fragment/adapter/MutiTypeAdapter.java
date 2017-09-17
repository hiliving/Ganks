package com.develop.hy.ganks.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.develop.hy.ganks.R;
import com.develop.hy.ganks.fragment.viewholder.BaseViewHolder;
import com.develop.hy.ganks.fragment.viewholder.CommonViewHolder;
import com.develop.hy.ganks.fragment.viewholder.GirlViewHolder;
import com.develop.hy.ganks.http.GankType;
import com.develop.hy.ganks.model.GankBean;

import java.util.List;

/**
 * Created by HY on 2017/9/14.
 */

public class MutiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder<List<GankBean.ResultsBean>>> {
    private Context context;
    private List<GankBean.ResultsBean> resultsBeen;
    private String itemType;

    public MutiTypeAdapter(Context context, List<GankBean.ResultsBean> results, String type) {
        this.context = context;
        this.resultsBeen = results;
        this.itemType = type;
    }

    @Override
    public BaseViewHolder<List<GankBean.ResultsBean>> onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType==1?new GirlViewHolder(listener,context,LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false))
                :new CommonViewHolder(listener,context,LayoutInflater.from(parent.getContext()).inflate(R.layout.girl_item,parent,false));

    }

    @Override
    public void onBindViewHolder(BaseViewHolder<List<GankBean.ResultsBean>> holder, int position) {
        holder.bindViewData(resultsBeen,position);
    }

    @Override
    public int getItemViewType(int position) {
        return itemType.equals(GankType.WELFARE)?1:2;
    }

    @Override
    public int getItemCount() {
        return resultsBeen.size();
    }
    public void setOnItemtClickListener(MutiTypeAdapter.OnItemClickListener listener){
        this.listener= listener;
    }
    private MutiTypeAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position,boolean isGirl);
    }

}
