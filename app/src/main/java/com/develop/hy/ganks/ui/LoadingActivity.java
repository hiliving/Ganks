package com.develop.hy.ganks.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.cache.DiskCacheManager;
import com.develop.hy.ganks.http.ApiService;
import com.develop.hy.ganks.http.UrlConfig;
import com.develop.hy.ganks.model.ConfigInfo;
import com.develop.hy.ganks.presenter.CommenInterface.ILoadingView;
import com.develop.hy.ganks.presenter.LoadingPresenter;
import com.develop.hy.ganks.utils.ToastUtils;
import com.yancy.gallerypick.config.GalleryPick;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 此页面用于请求启动页类型，检查更新，然后将结果传给下个页面
 * Created by HY on 2017/5/8.
 */

public class LoadingActivity extends BaseActivity implements ILoadingView {
    public static final int MODE_MOVIE = 2000;//视频
    public static final int MODE_PICTURE = 1000;//图片
    private final Handler mHandler = new Handler();
    private LoadingPresenter presenter;
    private Intent intent;

    @Override
    protected void onPreCreate() {
        super.onPreCreate();
    }
    @Override
    protected void initView() {
        presenter = new LoadingPresenter(this,this);
        presenter.init();
    }

    private void initCheckUpdate() {
        presenter.getUpdate();
        presenter.getConfig(this,intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.loading_layout;
    }

    @Override
    public void initViews() {
        intent = new Intent(LoadingActivity.this, SplashActivity.class);
        initCheckUpdate();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showErrorData() {
        //Todo这个得删掉，这里本来是放错误提示的。
        startNow();
    }

    @Override
    public void showNoMoreData() {

    }

    @Override
    public void showUpdate() {
        //按需进首页，调用 startAfterDelay();
        ToastUtils.showShortToast("发现新版本，普通更新");
    }

    @Override
    public void showMustUpdate() {
        //不进首页
        ToastUtils.showShortToast("发现新版本，强制更新");
    }

    @Override
    public void showNoUpdate() {
//        ToastUtils.showShortToast("不用更新");
        startAfterDelay();
    }

    @Override
    public void showErrorData(String error) {
        ToastUtils.showShortToast(error);
    }

    @Override
    public void showProgress() {

    }

    //未有更新时，调用此方法，3秒后进入首页
    public void startAfterDelay() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 1500l);
    }
    //临时代码，用于直接进入首页
    public void startNow() {
        startActivity(intent);
        finish();
    }

}
