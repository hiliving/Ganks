package com.develop.hy.ganks.dagger.component;

import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.dagger.module.LoginActivityModule;
import com.develop.hy.ganks.dagger.module.MainActivityModule;
import com.develop.hy.ganks.ui.LoginActivity;

import dagger.Component;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void in(MainActivity activity);
}
