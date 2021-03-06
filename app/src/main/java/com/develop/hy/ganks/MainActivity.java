package com.develop.hy.ganks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.develop.hy.ganks.dagger.component.DaggerMainActivityComponent;
import com.develop.hy.ganks.dagger.module.MainActivityModule;
import com.develop.hy.ganks.fragment.CommonFragment;
import com.develop.hy.ganks.fragment.adapter.ViewPageAdapter;
import com.develop.hy.ganks.http.GankType;
import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.model.NewsInfo;
import com.develop.hy.ganks.model.UserFile;
import com.develop.hy.ganks.presenter.CommenInterface.IFavoriteView;
import com.develop.hy.ganks.presenter.CommenInterface.IGanHuoView;
import com.develop.hy.ganks.presenter.FavoritePresenter;
import com.develop.hy.ganks.presenter.GankPresenter;
import com.develop.hy.ganks.ui.AboutUsActivity;
import com.develop.hy.ganks.ui.SearchActivity;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.develop.hy.ganks.http.GankType.ANDROID;
import static com.develop.hy.ganks.http.GankType.APP;
import static com.develop.hy.ganks.http.GankType.CASUAL;
import static com.develop.hy.ganks.http.GankType.EXTRA;
import static com.develop.hy.ganks.http.GankType.FRONTEND;
import static com.develop.hy.ganks.http.GankType.IOS;
import static com.develop.hy.ganks.http.GankType.VIDEO;
import static com.develop.hy.ganks.http.GankType.WELFARE;
import static com.develop.hy.ganks.utils.Utils.GetRoundedCornerBitmap;

/**
 *
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener ,IGanHuoView{


    private String currentFragmentTag;
    @BindView(R.id.floating_navigation_view)
    FloatingNavigationView floatView;
    @BindView(R.id.coorlayout)
    CoordinatorLayout coorlayout;
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
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.collapsedlayout)
    CollapsingToolbarLayout collapsedlayout;

    @Inject
    GankPresenter gankPresenter;

    private FragmentManager fragmentManager;
    private RelativeLayout layout;
    private RelativeLayout rl;
    private TextView userid;
    private ImageView userIcon;
    private ArrayList<String> imgList;
    private ViewPageAdapter viewPageAdapter;
    private List<NewsInfo.NewslistBean> resultList;
    private List<GankBean.ResultsBean> randomList;
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
    }

    private void initPagerView() {
        resultList = new ArrayList<>();
        randomList = new ArrayList<>();
        viewPageAdapter = new ViewPageAdapter(this, resultList,randomList);
        viewpager.setAdapter(viewPageAdapter);
    }

    private void initInject() {
        DaggerMainActivityComponent component = (DaggerMainActivityComponent) DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this,this))
                .build();
        component.in(this);
        gankPresenter.init();
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
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                break;
        }
    }

    @Override
    public void initViews() {
        gankPresenter.getRandom(GankType.WELFARE,10);
        gankPresenter.getNews(Constants.APP_KEY,10);
        initPagerView();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showErrorData() {

    }

    @Override
    public void showNoMoreData() {

    }

    @Override
    public void showListView(List<GankBean.ResultsBean> results) {
        randomList.clear();
        randomList.addAll(results);
        viewPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void initOthers(List<UserFile> list) {

        Glide.with(this).load(list.get(0).getHeadImg()).asBitmap()
                .placeholder(R.drawable.haveno_login)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        userIcon.setImageBitmap(GetRoundedCornerBitmap(resource,120));
                    }
                });
    }

    @Override
    public void showListView(NewsInfo newsInfo) {
        resultList.addAll(newsInfo.getNewslist());
        viewPageAdapter.notifyDataSetChanged();
    }


    private class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener{

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            float progress = Math.abs(verticalOffset)*1.0f/appBarLayout.getTotalScrollRange();
            if (progress>=0.4){
                ll_search.setVisibility(View.VISIBLE);
                ll_search.setAlpha(progress);
                titleText.setAlpha(1-progress);
                ll_search.setAlpha(progress);
            }else {
                ll_search.setVisibility(View.VISIBLE);
                ll_search.setAlpha(0.4f);
                titleText.setAlpha(1);
                titleText.setText(getTitle(currentFragmentTag));
            }
        }
    }

    private String getTitle(String currentFragmentTag) {
        switch (currentFragmentTag){
            case GankType.ANDROID:
                return "精选文章";
            case GankType.APP:
                return "移动开发";
            case GankType.CASUAL:
                return "随机推荐";
            case GankType.EXTRA:
                return "拓展资源";
            case GankType.FRONTEND:
                return "前端那些事";
            case GankType.IOS:
                return "IOS专区";
            case GankType.VIDEO:
                return "热点视频";
            case GankType.WELFARE:
                return "休息养眼";
        }
        return null;
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
               break;
           case R.id.nav_setting:
               startActivity(new Intent(this, AboutUsActivity.class));
               overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
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

    /**
     * 再按一次退出
     */
    private long mExitTime = 0;

    @Override
    public void onBackPressed() {
        if (floatView.isOpened()){
            floatView.close();
            return;
        }
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Snackbar.make(coorlayout, "再按一次退出噢~", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        }
    }
}
