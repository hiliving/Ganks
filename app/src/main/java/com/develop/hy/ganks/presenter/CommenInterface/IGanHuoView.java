package com.develop.hy.ganks.presenter.CommenInterface;

import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.model.UserFile;
import com.develop.hy.ganks.presenter.CommenInterface.IBaseView;

import java.util.List;

/**
 * Created by HY on 2017/9/13.
 */

public interface IGanHuoView extends IBaseView {

    void showListView(List<GankBean.ResultsBean> results);


    void initOthers(List<UserFile> list);
}
