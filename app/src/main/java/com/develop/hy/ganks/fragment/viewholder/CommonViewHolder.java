package com.develop.hy.ganks.fragment.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.fragment.adapter.MutiTypeAdapter;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.presenter.CommenInterface.OnItemClickListener;

import java.util.List;

/**
 * 通用的viewHolder
 * Created by HY on 2017/9/14.
 */

public class CommonViewHolder  extends BaseViewHolder<List<GankBean.ResultsBean>> {
    private final Context context;
    private final TextView tvtitle;
    private final TextView tvContent;
    private final CardView carview;
    private OnItemClickListener listener;
    private final TextView creattime;
    private final ImageView itemImg;

    public CommonViewHolder(OnItemClickListener listener, Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        tvtitle = (TextView) itemView.findViewById(R.id.tv_list_item_title);
        tvContent = (TextView) itemView.findViewById(R.id.tv_list_item_content);
        carview = (CardView) itemView.findViewById(R.id.carview);
        creattime = (TextView) itemView.findViewById(R.id.creattime);
        itemImg = (ImageView) itemView.findViewById(R.id.item_img);

    }

    @Override
    public void bindViewData(List<GankBean.ResultsBean> resultsBeen, final int position) {
        tvtitle.setText(resultsBeen.get(position).getDesc());
        tvContent.setText(resultsBeen.get(position).getWho());
        creattime.setText(resultsBeen.get(position).getPublishedAt());
        Glide.with(context).load(R.mipmap.haveno_login).into(itemImg);
        carview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position,false);
                }
            }
        });
    }
}
