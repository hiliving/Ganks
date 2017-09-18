package com.develop.hy.ganks.fragment.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.GankBean;

import java.util.List;

/**
 * Created by HY on 2017/9/18.
 */

public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private  List<GankBean.ResultsBean> resultBean;

    public ViewPageAdapter(Context context, List<GankBean.ResultsBean> resultBean) {
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
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_layout,null,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.page_img);
        TextView textView = (TextView) view.findViewById(R.id.pager_title);
        Glide.with(context).load(resultBean.get(position).getUrl()).into(imageView);
        textView.setText(resultBean.get(position).getWho()+"@"+resultBean.get(position).getPublishedAt());
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
