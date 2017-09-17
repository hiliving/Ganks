package com.develop.hy.ganks.presenter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.develop.hy.ganks.Constants;
import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.http.ApiManager;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoView;
import com.develop.hy.ganks.ui.LoginActivity;
import com.develop.hy.ganks.ui.UserCenter;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HY on 2017/9/12.
 */

public class GankPresenter<T> extends BasePresenter<IGanHuoView> implements Serializable{
    public GankPresenter(T context, IGanHuoView iView) {
        super((Context) context, iView);
    }

    @Override
    public void release() {
        unSubcription();
    }
    //获取数据
    public void loadGank(String type,int page){
        iView.showProgressBar();
        Subscription subscribe = ApiManager.getGankRetrofitInstance().getGank(type, Constants.PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankBean>() {

                    @Override
                    public void onCompleted() {
                        iView.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showErrorData();
                    }
                    @Override
                    public void onNext(GankBean gankDatas) {
                        if (gankDatas.isError()) {
                            iView.showNoMoreData();
                        } else {
                            iView.showListView(gankDatas.getResults());
                        }
                    }
                });
        addSubscription(subscribe);
    }
    //搜索
    public void queryGank(String keywords,int page){
        iView.showProgressBar();
        Subscription subscribe = ApiManager.getGankRetrofitInstance().queryGank(keywords, Constants.PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankBean>() {

                    @Override
                    public void onCompleted() {
                        iView.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showErrorData();
                    }
                    @Override
                    public void onNext(GankBean gankDatas) {
                        if (gankDatas.isError()) {
                            iView.showNoMoreData();
                        } else {
                            iView.showListView(gankDatas.getResults());
                        }
                    }
                });
        addSubscription(subscribe);
    }

    public void initUserInfo(final MainActivity activity, TextView userid, ImageView userIcon, RelativeLayout rl) {
        BmobUser bmobUser = BmobUser.getCurrentUser(activity);
        if (bmobUser!=null){
            userid.setText(bmobUser.getUsername()+"|已登录");
            userIcon.setBackgroundResource(R.mipmap.have_login);
        }else {
            userid.setText("个人中心|未登录");
            userIcon.setBackgroundResource(R.mipmap.haveno_login);
        }
        if (bmobUser!=null){
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity,UserCenter.class));
                }
            });
        }else {
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity,LoginActivity.class));
                }
            });
        }
    }
}
