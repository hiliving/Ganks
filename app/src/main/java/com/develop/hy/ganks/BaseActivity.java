package com.develop.hy.ganks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.develop.hy.ganks.theme.Theme;
import com.develop.hy.ganks.utils.PreUtils;


/**
 * Created by HY on 2017/9/12.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreate();
        setContentView(getLayoutId());
        initView();
    }

    protected abstract void initView();

    protected abstract int getLayoutId();
    protected void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {

        }
    }

    public void startActivity(Class<? extends AppCompatActivity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        if (finish)
            finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
