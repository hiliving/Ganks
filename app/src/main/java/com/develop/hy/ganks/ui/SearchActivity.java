package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.dagger.component.DaggerSearchActivityComponent;
import com.develop.hy.ganks.dagger.module.SearchActivityModule;
import com.develop.hy.ganks.fragment.adapter.MutiTypeAdapter;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoView;
import com.develop.hy.ganks.presenter.GankPresenter;
import com.develop.hy.ganks.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.develop.hy.ganks.Constants.searchHistory;

/**
 * Created by Helloworld on 2017/9/17.
 */

public class SearchActivity extends BaseActivity implements IGanHuoView, HXRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener,  MutiTypeAdapter.OnItemClickListener{

    private int page = 1;
    @Inject
    GankPresenter presenter;
    @BindView(R.id.recycleview)
    HXRecyclerView recyclerView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.search_now)
    Button search_now;
    private ArrayList<GankBean.ResultsBean> gankList;
    private String keyWords;
    private boolean canLoading = true;
    private boolean isRefresh = true;
    private MutiTypeAdapter adapter;


    @Override
    public void initViews() {
        keyWords = String.valueOf(et_search.getText());//搜索关键词
        gankList = new ArrayList<>();
        //这里虽然传入了关键词，在适配器里实际是起type的作用，影响到的是布局viewHolder
        adapter = new MutiTypeAdapter(this,gankList, keyWords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        adapter.setOnItemtClickListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        refreshLayout.setOnRefreshListener(this);
        search_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gankList.size()>0){
                    gankList.clear();
                    adapter.notifyDataSetChanged();
                }
                refreshLayout.setRefreshing(true);
                //这里关键词才发挥本身的作用
                presenter.queryGank(et_search.getText().toString(),page);
                searchHistory.add(et_search.getText().toString());
            }
        });
    }

    @Override
    public void showProgressBar() {
        if (!refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressBar() {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        HideOrShowKeyboard();
    }
    //隐藏虚拟键盘
    public  void HideOrShowKeyboard()
    {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
      // 显示或者隐藏输入法
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    public void showErrorData() {
        ToastUtils.showShortToast("加载出错");
        refreshLayout.setRefreshing(false);
        canLoading = false;
    }

    @Override
    public void showNoMoreData() {
        ToastUtils.showShortToast("没有更多啦");
        canLoading = false;
    }

    private void initInject() {
        DaggerSearchActivityComponent component= (DaggerSearchActivityComponent) DaggerSearchActivityComponent.builder()
                .searchActivityModule(new SearchActivityModule(this,this))
                .build();
        component.in(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initInject();
        presenter.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_layout;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.queryGank(keyWords,page);
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
    public void loadMore() {
        if (canLoading){
            presenter.queryGank(et_search.getText().toString(),page);
            canLoading = false;
        }
    }

    @Override
    public void onItemClick(int position, boolean isGirl) {
            startActivity(new Intent(this, WebViewActivity.class).putExtra("URL",gankList.get(position).getUrl()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }
}
