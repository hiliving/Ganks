package com.develop.hy.ganks.presenter.CommenInterface;
/**
 * Created by HY on 2017/4/12.
 */

public interface NewsListActivityInterface {

    void showProgressBar();

    void hideProgressBar();

    void loadData();

    void getDataSuccess(String zhihuStories);

    void getDataFail(String errMsg);

    void unSubcription();
}
