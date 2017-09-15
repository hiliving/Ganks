package com.develop.hy.ganks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.develop.hy.ganks.R;
import com.develop.hy.ganks.fragment.adapter.MutiTypeAdapter;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.presenter.GankPresenter;
import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoView;
import com.develop.hy.ganks.ui.HXRecyclerView;
import com.develop.hy.ganks.ui.WebViewActivity;
import com.develop.hy.ganks.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by HY on 2017/9/12.
 */

public class CommonFragment extends BaseFragment<GankPresenter> implements IGanHuoView, HXRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener,  MutiTypeAdapter.OnItemClickListener {
    @BindView(R.id.recycleview)
    HXRecyclerView recycleview;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.frglayout)
    FrameLayout frglayout;
    public static final String ARG_TYPE = "type";
    private  GankPresenter presenter;
    private ArrayList<GankBean.ResultsBean> gankList;
    private int page = 1;
    private boolean canLoading = true;
    private boolean isRefresh = true;
    private MutiTypeAdapter adapter;
    private String type;


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
    protected void initPresenter() {
        presenter = new GankPresenter(getActivity(),this);
        presenter.init();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            type = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public void initView() {
        gankList = new ArrayList<>();
        adapter = new MutiTypeAdapter(getContext(),gankList,type);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setAdapter(adapter);
        recycleview.setLoadMoreListener(this);
        adapter.setOnItemtClickListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadGank(type,page);
            }
        });
    }

    @Override
    public void showProgressBar() {
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressBar() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorData() {
        ToastUtils.showShortToast("加载出错");
        canLoading = true;
    }

    @Override
    public void showNoMoreData() {
        ToastUtils.showShortToast("没有更多啦");
        canLoading = false;
    }

    @Override
    public void showListView(List<GankBean.ResultsBean> results) {
        canLoading = true;
        page++;
        if (isRefresh) {
            this.gankList.clear();
            this.gankList.addAll(results);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            this.gankList.addAll(results);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.release();
    }

    @Override
    public void loadMore() {
        if (canLoading){
            presenter.loadGank(type,page);
            canLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.loadGank(type,page);
    }

    @Override
    public void onItemClick(int position) {
      startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("URL",gankList.get(position).getUrl()));
    }
}
