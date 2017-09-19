package com.develop.hy.ganks.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import me.wangyuwei.flipshare.FlipShareView;
import me.wangyuwei.flipshare.ShareItem;
import top.wefor.circularanim.CircularAnim;

import static com.develop.hy.ganks.utils.Utils.context;

/**
 * Created by HY on 2017/9/13.
 */

public class WebViewActivity extends BaseActivity {

    private static boolean SHOW = false;
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
               // ShareUtil.share(WebViewActivity.this,"["+getIntent().getStringExtra("Title")+"]"+getIntent().getStringExtra("URL")+"[分享自Ganks-by huangyong]");
                FlipShareView share = new FlipShareView.Builder(WebViewActivity.this, fab_add)
                        .addItem(new ShareItem("分享", Color.WHITE, 0xff43549C, BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_share)))
                        .addItem(new ShareItem("收藏", Color.WHITE, 0xff4999F0, BitmapFactory.decodeResource(getResources(), R.mipmap.heart)))
                        .addItem(new ShareItem("喜欢", Color.WHITE, 0xffD9392D, BitmapFactory.decodeResource(getResources(), R.mipmap.like)))
                        .setBackgroundColor(0x60000000)
                        .setItemDuration(500)
                        .setSeparateLineColor(0x30000000)
                        .setAnimType(FlipShareView.TYPE_VERTICLE)
                        .create();
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
