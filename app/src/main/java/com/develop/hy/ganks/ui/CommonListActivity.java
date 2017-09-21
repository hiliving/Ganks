package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.fragment.adapter.FavoriteAdapter;
import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.UserFile;
import com.develop.hy.ganks.presenter.CommenInterface.IFavoriteView;
import com.develop.hy.ganks.presenter.CommenInterface.OnItemClickListener;
import com.develop.hy.ganks.presenter.FavoritePresenter;
import com.develop.hy.ganks.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HY on 2017/9/19.
 */

public class CommonListActivity extends BaseActivity implements OnItemClickListener,IFavoriteView{
    @BindView(R.id.favorite_recycleview)
    RecyclerView favoriteRecycleview;
    @BindView(R.id.root_favorite)
    RelativeLayout rootFavorite;
    @BindView(R.id.favorite_nodata)
    TextView favoriteNodata;
    private FavoriteAdapter favoriteAdapter;
    private List<Favorite> favorites;
    private FavoritePresenter presenter;


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        presenter = new FavoritePresenter(this,this);
        presenter.init();
        Bundle bundle = getIntent().getBundleExtra("Data");
        favorites = (List<Favorite>) bundle.getSerializable("Favorite");
        favoriteRecycleview.setLayoutManager(new LinearLayoutManager(this));
        favoriteAdapter = new FavoriteAdapter(favorites,this);
        favoriteRecycleview.setAdapter(favoriteAdapter);
        favoriteAdapter.setOnItemtClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.favorit_layout;
    }

    @Override
    public void onItemClick(int position, boolean isGirl) {
        startActivity(new Intent(CommonListActivity.this, WebViewActivity.class).putExtra("URL",favorites
                .get(position).getUrl()).putExtra("Title",favorites.get(position).getTitle())
                .putExtra("Imgs",favorites.get(position).getImgs())
        );
    }

    @Override
    public void onDeleteItem(final int position) {
        Snackbar.make(rootFavorite, "确定删除？", Snackbar.LENGTH_LONG).setAction("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.deleteFavorite(position,favorites);
                        favorites.remove(position);
                        favoriteAdapter.notifyItemChanged(position);
                        Log.d("USERCENTER",favorites.size()+"");
                        if (favorites.size()==0){
                            favoriteNodata.setVisibility(View.VISIBLE);
                        }
                    }
                }).show();
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

    }

    @Override
    public void initUserInfo(List<UserFile> favorites) {

    }
}
