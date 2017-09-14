package com.develop.hy.ganks.presenter;

import android.content.Context;

import com.develop.hy.ganks.Constants;
import com.develop.hy.ganks.http.ApiManager;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoGirl;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HY on 2017/9/14.
 */

public class GirlPresenter extends BasePresenter<IGanHuoGirl> {
    public GirlPresenter(Context context, IGanHuoGirl iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        unSubcription();
    }
    public void loadGirl(String type,int page){
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
}
