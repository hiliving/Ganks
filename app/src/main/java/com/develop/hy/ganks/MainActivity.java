package com.develop.hy.ganks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.develop.hy.ganks.daggerExamples.DaggerMainActivityComponent;
import com.develop.hy.ganks.daggerExamples.LoginPresenter;
import com.develop.hy.ganks.daggerExamples.MainActivityModule;
import com.develop.hy.ganks.fragment.CommonFragment;
import com.develop.hy.ganks.ui.LoginActivity;
import com.develop.hy.ganks.ui.UserCenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

import static com.develop.hy.ganks.http.GankType.ANDROID;
import static com.develop.hy.ganks.http.GankType.APP;
import static com.develop.hy.ganks.http.GankType.CASUAL;
import static com.develop.hy.ganks.http.GankType.EXTRA;
import static com.develop.hy.ganks.http.GankType.FRONTEND;
import static com.develop.hy.ganks.http.GankType.IOS;
import static com.develop.hy.ganks.http.GankType.VIDEO;
import static com.develop.hy.ganks.http.GankType.WELFARE;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Inject
    LoginPresenter presenter;

    private String currentFragmentTag;

    @BindView(R.id.floating_navigation_view)
    FloatingNavigationView floatView;
    @BindView(R.id.toobar)
    Toolbar toolbar;


    private FragmentManager fragmentManager;
    private RelativeLayout layout;
    private RelativeLayout rl;
    private TextView userid;
    private ImageView userIcon;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        Inject();
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
        //dagger演示代码的调用
//        presenter.login("name","pwd");
        switchFragment(ANDROID);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initToolBar(){
        setSupportActionBar(toolbar);
    }
    private void Inject() {
        //dagger演示代码
        DaggerMainActivityComponent component = (DaggerMainActivityComponent) DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build();
        component.in(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        floatView.close();
       switch (item.getItemId()){
           case R.id.nav_all:
               switchFragment("all");
               break;
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
               break;
       }
        return false;
    }
    public void switchFragment(String name){
        setTitle(name);

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
            if (name.equals("all")){
              //  newFragment = new GirlFragment();
            }else if (name.equals("福利")){
               // newFragment = new FuLiFragment();
                newFragment = CommonFragment.newInstance(name);
            }else {
                newFragment = CommonFragment.newInstance(name);
            }
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
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        if (bmobUser!=null){
            userid.setText(bmobUser.getUsername());
            userIcon.setBackgroundResource(R.mipmap.ic_launcher_round);
        }
        if (bmobUser!=null){
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,UserCenter.class));
                }
            });
        }else {
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            });
        }
    }
}
