package com.develop.hy.ganks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.develop.hy.ganks.theme.Theme;
import com.develop.hy.ganks.utils.PreUtils;


/**
 * Created by HY on 2017/9/12.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreate();
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(getLayoutId());
        initView();
    }

    protected abstract void initView();

    protected abstract int getLayoutId();
    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
