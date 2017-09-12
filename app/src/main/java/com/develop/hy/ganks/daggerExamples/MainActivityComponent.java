package com.develop.hy.ganks.daggerExamples;

import com.develop.hy.ganks.MainActivity;

import dagger.Component;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void in(MainActivity activity);
}
