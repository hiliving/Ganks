package com.develop.hy.ganks.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.utils.ShareUtil;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.just.library.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.develop.hy.ganks.utils.Utils.context;

/**
 * Created by HY on 2017/9/13.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.rootlayout)
    RelativeLayout weblayout;
    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;
    private AgentWeb mAgentWeb;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(weblayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(new ChromeClientCallbackManager.ReceivedTitleCallback() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        setTitle(title);
                    }
                }) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(getIntent().getStringExtra("URL"));
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.share(WebViewActivity.this, R.string.string_share_text);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.webview_layout;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
