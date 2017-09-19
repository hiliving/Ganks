package com.develop.hy.ganks.presenter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
import com.develop.hy.ganks.ui.UserCenterActivity;
import com.develop.hy.ganks.utils.Utils;

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
    public void loadGank(String type,int PageSize,int page){
        iView.showProgressBar();
        Subscription subscribe = ApiManager.getGankRetrofitInstance().getGank(type, PageSize, page)
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
                           // Log.d("LOG",gankDatas.getResults().get(0).getImages().toString());
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
    //随机数据
    public void getRandom(String category,int count){
        iView.showProgressBar();
        Subscription subscribe = ApiManager.getGankRetrofitInstance().queryRandom(category, count)
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
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),R.mipmap.haveno_login);
        Bitmap roundIcon = Utils.GetRoundedCornerBitmap(bitmap);
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser!=null){
            userid.setText(bmobUser.getUsername()+"|已登录");
            userIcon.setImageBitmap(roundIcon);
        }else {
            userid.setText("个人中心|未登录");

            userIcon.setImageBitmap(roundIcon);
        }
        if (bmobUser!=null){
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity,UserCenterActivity.class));
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
