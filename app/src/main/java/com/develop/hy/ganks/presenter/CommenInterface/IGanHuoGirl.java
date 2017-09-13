package com.develop.hy.ganks.presenter.CommenInterface;

import com.develop.hy.ganks.model.GankBean;

import java.util.List;

/**
 * Created by HY on 2017/9/13.
 */

public interface IGanHuoGirl extends IBaseView {
    void showListView(List<GankBean.ResultsBean> results);
}
