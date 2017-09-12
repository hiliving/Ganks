package com.develop.hy.ganks.presenter;

import com.develop.hy.ganks.http.GankRequest;
import com.develop.hy.ganks.presenter.CommenInterface.NewsListActivityInterface;

/**
 * Created by HY on 2017/9/12.
 */

public class GankPresenter extends BasePresenter {
    private NewsListActivityInterface listActivity;
    private GankRequest gankRequest;
    public GankPresenter(NewsListActivityInterface listActivity) {
        this.listActivity = listActivity;
        gankRequest = new GankRequest();
    }
}
