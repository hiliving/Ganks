package com.develop.hy.ganks;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.develop.hy.ganks.dagger.component.DaggerMainActivityComponent;
import com.develop.hy.ganks.dagger.module.MainActivityModule;
import com.develop.hy.ganks.fragment.CommonFragment;
import com.develop.hy.ganks.presenter.GankPresenter;
import com.develop.hy.ganks.ui.SearchActivity;
import com.develop.hy.ganks.ui.SettingActivity;
import com.develop.hy.ganks.utils.ToastUtils;
import com.just.library.LogUtils;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.develop.hy.ganks.R.id.collapsedlayout;
import static com.develop.hy.ganks.http.GankType.ANDROID;
import static com.develop.hy.ganks.http.GankType.APP;
import static com.develop.hy.ganks.http.GankType.CASUAL;
import static com.develop.hy.ganks.http.GankType.EXTRA;
import static com.develop.hy.ganks.http.GankType.FRONTEND;
import static com.develop.hy.ganks.http.GankType.IOS;
import static com.develop.hy.ganks.http.GankType.VIDEO;
import static com.develop.hy.ganks.http.GankType.WELFARE;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private String currentFragmentTag;

    @BindView(R.id.floating_navigation_view)
    FloatingNavigationView floatView;
    @BindView(R.id.toobar)
    Toolbar toolbar;
    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.edit_search)
    TextView edit_search;
    @BindView(R.id.appBarlayout)
    AppBarLayout appBarlayout;
    @BindView(R.id.heder_pic)
    ImageView heder_pic;
    @BindView(R.id.collapsedlayout)
    CollapsingToolbarLayout collapsedlayout;

    @Inject
    GankPresenter gankPresenter;

    private FragmentManager fragmentManager;
    private RelativeLayout layout;
    private RelativeLayout rl;
    private TextView userid;
    private ImageView userIcon;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initInject();
        fragmentManager = getSupportFragmentManager();
        initToolBar();
        //悬浮按钮点击
        layout = (RelativeLayout) floatView.getHeaderView(0);
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatView.open();
            }
        });
        rl = (RelativeLayout) layout.findViewById(R.id.usercenter);
        userid = (TextView) layout.findViewById(R.id.userid);
        userIcon = (ImageView) layout.findViewById(R.id.userIcon);

        //悬浮按钮条目监听
        floatView.setNavigationItemSelectedListener(this);
        edit_search.setOnClickListener(this);
        //默认加载一个页面
        switchFragment(ANDROID);
        //
        heder_pic.setImageResource(R.mipmap.e);
    }

    private void initInject() {
        DaggerMainActivityComponent component = (DaggerMainActivityComponent) DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build();
        component.in(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initToolBar(){
        setSupportActionBar(toolbar);
        appBarlayout.addOnOffsetChangedListener(new MyOffsetChangedListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }

    private class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener{

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (Math.abs(verticalOffset)>=500){
                ll_search.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.GONE);
            }else {
                ll_search.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.VISIBLE);
                titleText.setText(currentFragmentTag+"专题");
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        floatView.close();
       switch (item.getItemId()){
           case R.id.nav_gift:
               switchFragment(WELFARE);
               break;
           case R.id.nav_android:
               switchFragment(ANDROID);
               break;
           case R.id.nav_ios:
               switchFragment(IOS);
               break;
           case R.id.nav_movie:
               switchFragment(VIDEO);
               break;
           case R.id.nav_web:
               switchFragment(FRONTEND);
               break;
           case R.id.nav_more:
               switchFragment(EXTRA);
               break;
           case R.id.nav_app:
               switchFragment(APP);
               break;
           case R.id.nav_reco:
               switchFragment(CASUAL);
           case R.id.nav_setting:
//               startActivity(new Intent(this, SettingActivity.class));
               ToastUtils.showShortToast("功能正在开发中");
               break;
       }
        return false;
    }

    public void switchFragment(String name){
        setTitle(name);
        Log.d("Title",name);
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        Fragment newFragment = fragmentManager.findFragmentByTag(name);

        if (newFragment == null) {
                newFragment = CommonFragment.newInstance(name);
        }

        if (newFragment.isAdded()) {
            ft.show(newFragment);
        } else {
            ft.add(R.id.fragment_container, newFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gankPresenter.initUserInfo(this,userid,userIcon,rl);
    }
}
