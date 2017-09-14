package com.develop.hy.ganks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.develop.hy.ganks.daggerExamples.DaggerMainActivityComponent;
import com.develop.hy.ganks.daggerExamples.LoginPresenter;
import com.develop.hy.ganks.daggerExamples.MainActivityModule;
import com.develop.hy.ganks.fragment.AllFragment;
import com.develop.hy.ganks.fragment.CommonFragment;
import com.develop.hy.ganks.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Inject
    LoginPresenter presenter;
    private String currentFragmentTag;

    @BindView(R.id.floating_navigation_view)
    FloatingNavigationView floatView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        Inject();

        //悬浮按钮点击
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatView.open();
            }
        });
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        //悬浮按钮条目监听
        floatView.setNavigationItemSelectedListener(this);
        //dagger演示代码的调用
        presenter.login("name","pwd");

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
               switchFragment("福利");
               break;
           case R.id.nav_android:
               switchFragment("android");
               break;
           case R.id.nav_ios:
               switchFragment("ios");
               break;
           case R.id.nav_movie:
               switchFragment("休息视频");
               break;
           case R.id.nav_web:
               switchFragment("前端");
               break;
           case R.id.nav_more:
               switchFragment("拓展资源");
               break;
           case R.id.nav_app:
               switchFragment("App");
               break;
           case R.id.nav_reco:
               switchFragment("瞎推荐");
               break;
       }
        return false;
    }
    public void switchFragment(String name){
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        Fragment foundFragment = fragmentManager.findFragmentByTag(name);

        if (foundFragment == null) {
            if (name.equals("all")){
                foundFragment = new AllFragment();
            }else if (name.equals("福利")){
               // foundFragment = new FuLiFragment();
            }else {
                foundFragment = CommonFragment.newInstance(name);
            }
        }

        if (foundFragment == null) {

        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.fragment_container, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }

}
