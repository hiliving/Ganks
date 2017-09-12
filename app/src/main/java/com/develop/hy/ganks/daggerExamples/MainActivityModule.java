package com.develop.hy.ganks.daggerExamples;

import com.develop.hy.ganks.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Module
public class MainActivityModule {
    MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Provides
    LoginPresenter provideLoginPresenter(){
        return new LoginPresenter(mainActivity);
    }
}
