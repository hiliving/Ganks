package com.develop.hy.ganks.fragment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.model.NewsInfo;
import com.develop.hy.ganks.ui.WebViewActivity;

import java.util.List;

/**
 * Created by HY on 2017/9/18.
 */

public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private  List<NewsInfo.NewslistBean> resultBean;

    public ViewPageAdapter(Context context, List<NewsInfo.NewslistBean> resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    @Override
    public int getCount() {
        return resultBean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_layout,null,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.page_img);
        TextView textView = (TextView) view.findViewById(R.id.pager_title);
        Glide.with(context).load(resultBean.get(position).getPicUrl())
                .placeholder(R.mipmap.e)
                .into(imageView);
        textView.setText(resultBean.get(position).getTitle()+"\n@"+
                        resultBean.get(position).getDescription()+
                resultBean.get(position).getCtime());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, WebViewActivity.class)
                        .putExtra("URL",resultBean
                                .get(position).getUrl())
                        .putExtra("Title",resultBean.get(position).getTitle())
                        .putExtra("Author",resultBean.get(position).getDescription())
                        .putExtra("Imgs",resultBean.get(position).getPicUrl()==null?"":resultBean.get(position).getPicUrl())
                );
                ((Activity)context).overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            }
        });
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
