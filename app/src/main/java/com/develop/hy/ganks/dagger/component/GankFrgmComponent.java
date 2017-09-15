package com.develop.hy.ganks.dagger.component;

import android.content.Context;

import com.develop.hy.ganks.dagger.module.GankFragmodule;

import dagger.Component;

/**
 * Created by Helloworld on 2017/9/12.
 */
@Component(modules = GankFragmodule.class)
public interface GankFrgmComponent {
    void in(Context activity);
}
