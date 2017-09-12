package com.develop.hy.ganks.presenter.CommenInterface;


/**
 *
 */

public interface NewsDetailInterface {

    void showProgressBar();

    void hideProgressBar();

    void loadData();

    void getDataFail(String errMsg);

    void getDataSuccess(String zhihuStory);

    void unSubcription();
}
