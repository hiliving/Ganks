package com.develop.hy.ganks.fragment.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.develop.hy.ganks.model.GankBean;

import java.util.List;

/**
 * Created by HY on 2017/9/14.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindViewData(List<GankBean.ResultsBean> resultsBeen, int position);
}
