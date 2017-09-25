package com.develop.hy.ganks.presenter;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.develop.hy.ganks.App;
import com.develop.hy.ganks.presenter.CommenInterface.IBaseView;
import com.develop.hy.ganks.utils.ToastUtils;

import me.xiaopan.android.net.NetworkUtils;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by HY on 2017/4/12.
 */

public abstract class BasePresenter<T extends IBaseView> {

    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {

        this.context = context;
        this.iView = iView;
    }
    public void init(){
        iView.initViews();
    }
    public void onDestroy(){
        iView=null;
    }

    public abstract void release();

    //将所有正在处理的Subcription都添加到CompositeSubscription中，统一退出的时候注销观察者
    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
    //在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止RX造成的内存泄露
    public void unSubcription(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
