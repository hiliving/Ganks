package com.develop.hy.ganks.dagger.module;

import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.dagger.LoginPresenter;
import com.develop.hy.ganks.dagger.MainPresenter;
import com.develop.hy.ganks.ui.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Module
public class MainActivityModule {
    MainActivity mainActivity;

    public MainActivityModule(MainActivity loginActivity) {
        this.mainActivity = loginActivity;
    }
    @Provides
    MainPresenter provideLoginPresenter(){
        return new MainPresenter(mainActivity);
    }
}
