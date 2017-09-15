package com.develop.hy.ganks.dagger.module;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoView;
import com.develop.hy.ganks.presenter.GankPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HY on 2017/9/15.
 */
@Module
public class GankFragmodule {
    FragmentActivity context;
    private IGanHuoView view;
    public GankFragmodule(FragmentActivity context) {
        this.context = context;
    }
    @Provides
    GankPresenter provideGankPresenter(){
        return new GankPresenter(context, view);
    }
}
