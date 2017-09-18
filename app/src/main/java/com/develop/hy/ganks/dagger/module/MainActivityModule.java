package com.develop.hy.ganks.dagger.module;

import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoView;
import com.develop.hy.ganks.presenter.GankPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Module
public class MainActivityModule {
    MainActivity mainActivity;
    private IGanHuoView view;
    public MainActivityModule(MainActivity loginActivity,IGanHuoView view) {
        this.mainActivity = loginActivity;
        this.view = view;
    }
    @Provides
    GankPresenter provideGankPresenter(){
        return new GankPresenter(mainActivity, view);
    }
}
