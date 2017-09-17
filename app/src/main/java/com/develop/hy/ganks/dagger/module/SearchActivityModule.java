package com.develop.hy.ganks.dagger.module;

import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoView;
import com.develop.hy.ganks.presenter.GankPresenter;
import com.develop.hy.ganks.ui.SearchActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Module
public class SearchActivityModule {
    SearchActivity searchActivity;
    private IGanHuoView view;
    public SearchActivityModule(SearchActivity searchActivity, IGanHuoView iGanHuoView) {
        this.searchActivity = searchActivity;
        this.view = iGanHuoView;
    }
    @Provides
    GankPresenter provideGankPresenter(){
        return new GankPresenter(searchActivity, view);
    }
}
