package com.develop.hy.ganks.fragment;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.develop.hy.ganks.R;
import com.develop.hy.ganks.fragment.adapter.ListAdapter;

import butterknife.BindView;

/**
 * Created by HY on 2017/9/12.
 */

public class CommonFragment extends BaseFragment {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.commonlist)
    ListView commonList;
    public static final String ARG_TYPE = "type";
    public static CommonFragment newInstance(String type) {

        Bundle args = new Bundle();

        CommonFragment fragment = new CommonFragment();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_layout;
    }

    @Override
    protected void initViews() {
        tv.setText(this.getArguments().get(ARG_TYPE)+"");
        commonList.setAdapter(new ListAdapter(getContext()));
    }

    @Override
    protected void lazyFetchData() {

    }
}
