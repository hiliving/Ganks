package com.develop.hy.ganks.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.develop.hy.ganks.R;

import butterknife.BindView;

/**
 * Created by HY on 2017/9/12.
 */

public class CommonFragment extends BaseFragment {
    @BindView(R.id.tv)
    TextView tv;

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
    }

    @Override
    protected void lazyFetchData() {

    }
}
