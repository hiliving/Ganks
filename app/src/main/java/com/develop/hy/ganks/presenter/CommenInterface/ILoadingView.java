package com.develop.hy.ganks.presenter.CommenInterface;

/**
 * Created by HY on 2017/9/22.
 */

public interface ILoadingView extends IBaseView{
    void showUpdate();
    void showMustUpdate();
    void showNoUpdate();
    void showErrorData(String error);


    void showProgress(String percent);
}
