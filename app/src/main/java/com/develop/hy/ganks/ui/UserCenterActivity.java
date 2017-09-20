package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.presenter.CommenInterface.IFavoriteView;
import com.develop.hy.ganks.presenter.FavoritePresenter;
import com.develop.hy.ganks.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by HY on 2017/9/14.
 */

public class UserCenterActivity extends BaseActivity implements View.OnClickListener,IFavoriteView {

    @BindView(R.id.bt_logout)
    Button logout;
    @BindView(R.id.bt_collect)
    TextView collect;
    @BindView(R.id.user_toolbar)
    Toolbar userToolbar;
    @BindView(R.id.bt_clear_cache)
    TextView clear;
    @BindView(R.id.tv_username)
    TextView username;
    private FavoritePresenter presenter;
    private ArrayList<Favorite> list= new ArrayList<>();;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initEvent();
        setTitle("");
        setSupportActionBar(userToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BmobUser bmobUser = BmobUser.getCurrentUser();
        username.setText(bmobUser.getUsername());
        presenter = new FavoritePresenter(this,this);
        presenter.init();
    }

    private void initEvent() {
        logout.setOnClickListener(this);
        collect.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.usercenter_layout;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_logout:
                BmobUser.logOut();
                finish();
                break;
            case R.id.bt_collect:
                getFavorite();
                break;
            case R.id.bt_clear_cache:
                ToastUtils.showShortToast("功能正在开发中");
                break;
        }
    }

    private void getFavorite() {
        if (list.size()==0){
            ToastUtils.showShortToast("当前还没有收藏任何内容");
            return;
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Favorite", (Serializable) list);
            startActivity(new Intent(UserCenterActivity.this, CommonListActivity.class)
                    .putExtra("Data",bundle));
        }
    }


    @Override
    public void initViews() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showErrorData() {

    }

    @Override
    public void showNoMoreData() {

    }

    @Override
    public void initData(List<Favorite> favorites) {
        list.clear();
        list.addAll(favorites);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getFavorite();
    }
}
