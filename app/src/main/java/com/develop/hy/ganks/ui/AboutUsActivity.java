package com.develop.hy.ganks.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.Constants;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.presenter.CommenInterface.ILoadingView;
import com.develop.hy.ganks.presenter.UpdatePresenter;
import com.develop.hy.ganks.ui.view.DownloadService;
import com.develop.hy.ganks.utils.AlipayZeroSdk;
import com.develop.hy.ganks.utils.ToastUtils;
import com.develop.hy.ganks.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class AboutUsActivity extends BaseActivity implements ILoadingView{

    @BindView(R.id.us_toolbar)
    Toolbar usToolbar;
    @BindView(R.id.encourage)
    TextView encourage;
    @BindView(R.id.mycsdn)
    TextView mycsdn;
    @BindView(R.id.mygithub)
    TextView mygithub;
    @BindView(R.id.suggest)
    TextView suggest;
    @BindView(R.id.bt_upgrade)
    Button bt_upgrade;
    @BindView(R.id.content_root)
    CoordinatorLayout cor_root;
    private UpdatePresenter updatePresenter;
    private ProgressDialog progressBar;
    private String cacheSize;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(usToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            }
        });
        initProgressBar();
        updatePresenter = new UpdatePresenter(this,this);

        mygithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","https://github.com/hiliving"));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            }
        });
        mycsdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","http://blog.csdn.net/huang_yong_"));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            }
        });
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("功能正在开发中……");
            }
        });
        encourage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlipayZeroSdk.hasInstalledAlipayClient(AboutUsActivity.this)) {
                    AlipayZeroSdk.startAlipayClient(AboutUsActivity.this, "FKX05183IOKBWDUKV9OF03");
                } else {
                    Snackbar.make(cor_root, "谢谢，您没有安装支付宝客户端", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        bt_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePresenter.getUpdate();
            }
        });
    }

    private void initCacheSize() {
        try {
            long cacheSzie = getFolderSize(this.getCacheDir());
            cacheSize = Utils.getFormatSize(cacheSzie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initProgressBar() {
        Constants.IF_DOWNLOAD = true;//重置下载控制开关
        EventBus.getDefault().register(this);
        progressBar = new ProgressDialog(AboutUsActivity.this);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setCancelable(false);
        progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Constants.IF_DOWNLOAD = false;//取消下载，停止下载操作
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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
    }

    @Override
    public void initViews() {

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

    }

    @Override
    public void showNoMoreData() {

    }

    @Override
    public void showUpdate() {

    }

    @Override
    public void showMustUpdate() {

    }

    @Override
    public void showNoUpdate() {
        ToastUtils.showShortToast("当前已是最新版本");
    }

    @Override
    public void showErrorData(String error) {

    }

    @Override
    public void showProgress(String percent) {

    }
    //获取文件大小
    private long getFolderSize(java.io.File file) throws Exception {
        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {

                //如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
