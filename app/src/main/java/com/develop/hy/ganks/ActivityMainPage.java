package com.develop.hy.ganks;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.develop.hy.ganks.presenter.CommenInterface.NewsListActivityInterface;

/**
 * Created by HY on 2017/9/12.
 */

public class ActivityMainPage extends BaseActivity  implements NewsListActivityInterface{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void getDataSuccess(String zhihuStories) {

    }

    @Override
    public void getDataFail(String errMsg) {

    }

    @Override
    public void unSubcription() {

    }
}
