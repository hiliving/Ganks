package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.databinding.WebviewLayoutBinding;
import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.utils.ShareUtil;
import com.develop.hy.ganks.utils.ToastUtils;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HY on 2017/9/13.
 */

public class WebViewActivity extends BaseActivity {
    private boolean isFabMenuOpen = false;
    private static boolean SHOW = false;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.rootlayout)
    RelativeLayout webrootview;
    @BindView(R.id.weblayout)
    RelativeLayout rootview;


    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;
    private AgentWeb mAgentWeb;
    private WebviewLayoutBinding binding;
    private Animation fabOpenAnimation;
    private Animation fabCloseAnimation;
    @Override
    protected void initView() {
        ButterKnife.bind(this);

        binding = DataBindingUtil.setContentView(WebViewActivity.this, R.layout.webview_layout);
        binding.setFabHandler(new FabHandler());
        getAnimations();

        mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(binding.rootlayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
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
    }


    @Override
    protected int getLayoutId() {
        return R.layout.webview_layout;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
        Log.d("AAAAAAAAAAAAAAAAA","销毁了");
    }

    private void getAnimations() {

        fabOpenAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_open);

        fabCloseAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_close);

    }
    public class FabHandler {

        public void onBaseFabClick(View view) {

            if (isFabMenuOpen)
                collapseFabMenu();
            else
                expandFabMenu();
        }

        public void onFavoriteFabClick(final View view) {

            User user =BmobUser.getCurrentUser(User.class);
            if (user==null){
                startActivity(new Intent(WebViewActivity.this,LoginActivity.class));
            }else {
                Favorite favorite = new Favorite();
                favorite.setTitle(getIntent().getStringExtra("Title"));
                favorite.setUrl(getIntent().getStringExtra("URL"));
                favorite.setUserId(user);
                favorite.setImgs(getIntent().getStringExtra("Imgs"));
                favorite.setAuthor(getIntent().getStringExtra("Author"));
                favorite.save(new SaveListener<String>() {
                                  @Override
                                  public void done(String s, BmobException e) {
                                      Snackbar.make(view, "添加收藏成功~", Toast.LENGTH_SHORT).show();
                                  }
                              }
                );
            }
        }

        public void onShareFabClick(View view) {
            ShareUtil.share(WebViewActivity.this,"["+getIntent().getStringExtra("Title")+"]"+getIntent().getStringExtra("URL")+"[分享自Ganks-by huangyong]");
        }
    }
    private void collapseFabMenu() {

        ViewCompat.animate(binding.fabAdd).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        binding.createLayout.startAnimation(fabCloseAnimation);
        binding.shareLayout.startAnimation(fabCloseAnimation);
        binding.createFab.setClickable(false);
        binding.shareFab.setClickable(false);
        isFabMenuOpen = false;
    }
    private void expandFabMenu() {

        ViewCompat.animate(binding.fabAdd).rotation(45.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        binding.createLayout.startAnimation(fabOpenAnimation);
        binding.shareLayout.startAnimation(fabOpenAnimation);
        binding.createFab.setClickable(true);
        binding.shareFab.setClickable(true);
        isFabMenuOpen = true;


    }

}
