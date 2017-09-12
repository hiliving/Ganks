package com.develop.hy.ganks.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;

/**
 * Created by HY on 2017/9/12.
 */

public class BaseListFragment<T> extends BaseFragment implements OnRefreshListener,OnLoadMoreListener {




    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void lazyFetchData() {

    }
}
