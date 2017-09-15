package com.develop.hy.ganks.dagger.module;

import com.develop.hy.ganks.dagger.LoginPresenter;
import com.develop.hy.ganks.ui.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Module
public class LoginActivityModule {
    LoginActivity loginActivity;

    public LoginActivityModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }
    @Provides
    LoginPresenter provideLoginPresenter(){
        return new LoginPresenter(loginActivity);
    }
}
