package com.develop.hy.ganks.dagger.component;

import com.develop.hy.ganks.dagger.module.LoginActivityModule;
import com.develop.hy.ganks.ui.LoginActivity;

import dagger.Component;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Component(modules = LoginActivityModule.class)
public interface LoginActivityComponent {
    void in(LoginActivity activity);
}
