package com.develop.hy.ganks.fragment.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.fragment.adapter.MutiTypeAdapter;
import com.develop.hy.ganks.model.GankBean;

import java.util.List;

/**
 * Created by HY on 2017/9/14.
 */

public class GirlViewHolder extends BaseViewHolder<List<GankBean.ResultsBean>> {
    TextView tv_list_author;
    TextView tv_list_content;
    Context context;
    private MutiTypeAdapter.OnItemClickListener listener;
    ImageView pic;
    public GirlViewHolder(MutiTypeAdapter.OnItemClickListener listener, Context context, View itemView) {
        super(itemView);
        this.context = context;
        tv_list_author = (TextView) itemView.findViewById(R.id.tv_list_author);
        tv_list_content = (TextView) itemView.findViewById(R.id.tv_list_content);
        pic = (ImageView) itemView.findViewById(R.id.pic);
        this.listener = listener;
    }

    @Override
    public void bindViewData(List<GankBean.ResultsBean> resultsBeen, final int position) {
        tv_list_content.setText(resultsBeen.get(position).getDesc());
        tv_list_author.setText(resultsBeen.get(position).getWho());
        pic.setImageResource(R.mipmap.ic_launcher_round);
        Glide.with(context)
                .load(resultsBeen.get(position).getUrl()+"")
                .crossFade()
                .into(pic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position,true);
                }
            }
        });
    }

}
