package com.develop.hy.ganks.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.Constants;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.presenter.CommenInterface.ILoadingView;
import com.develop.hy.ganks.presenter.LoadingPresenter;
import com.develop.hy.ganks.ui.view.DownloadService;
import com.develop.hy.ganks.utils.ToastUtils;
import com.just.library.LogUtils;

import de.greenrobot.event.EventBus;

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
    private ProgressDialog progressBar;

    @Override
    protected void onPreCreate() {
        super.onPreCreate();
    }
    @Override
    protected void initView() {

        presenter = new LoadingPresenter(this,this);
        presenter.init();

        EventBus.getDefault().register(this);
        progressBar = new ProgressDialog(LoadingActivity.this);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setCancelable(false);
        progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Constants.IF_DOWNLOAD = false;
            }
        });
    }
    /**
     *
     * @param event
     */
    public void onEventMainThread(DownloadService.MessageEvent event) {

        if (event == null) {
            return;
        }
        Log.d("TTTTTT",event.speed+"");
        if (progressBar!=null)
        progressBar.setProgress(Integer.parseInt(event.speed));

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
        progressBar.setMessage("正在下载安装包，请稍后……");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.show();
    }

    @Override
    public void hideProgressBar() {
        progressBar.cancel();
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
    public void showProgress(String percent) {

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

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
