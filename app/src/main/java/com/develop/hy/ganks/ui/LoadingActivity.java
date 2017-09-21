package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.http.ApiService;
import com.develop.hy.ganks.http.UrlConfig;
import com.develop.hy.ganks.model.ConfigInfo;

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

public class LoadingActivity extends BaseActivity {
    public static final int MODE_MOVIE = 2000;//视频
    public static final int MODE_PICTURE = 1000;//图片
    private final Handler mHandler = new Handler();
    private Intent intent;

    @Override
    protected void onPreCreate() {
        super.onPreCreate();
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }
    @Override
    protected void initView() {
        intent = new Intent(this, SplashActivity.class);

        initConfig();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.loading_layout;
    }

    private void initConfig() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UrlConfig.baseConfig)
                .build();

        final ApiService mApi = retrofit.create(ApiService.class);
        Call<ConfigInfo> request = mApi.getType();

        //启动即发请求
        request.enqueue(new Callback<ConfigInfo>() {
            @Override
            public void onResponse(Call<ConfigInfo> call, Response<ConfigInfo> response) {

                ConfigInfo mInfo = response.body();//一行代码将返回结果完成封装

                String fileUrl = mInfo.getData().getFileUrl();
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                String filePath = LoadingActivity.this.getCacheDir().getPath() + "/splash";//这里用的内置sd卡

                if (mInfo.getData().getFileType() == 2) {
                    //启动为视频
                   // downRes(fileUrl, fileName, filePath, MODE_MOVIE);
                    intent.putExtra("type", MODE_MOVIE);
                } else {
                    //启动为图片
                    downRes(fileUrl, fileName, filePath, MODE_PICTURE);
                    intent.putExtra("type", MODE_PICTURE);
                }
                //3秒后跳转
                startAfterDelay();
            }

            @Override
            public void onFailure(Call<ConfigInfo> call, Throwable t) {
            }
        });
    }

    /**
     * 下载启动资源
     * @param url
     * @param fileName
     * @param path
     * @param type
     */
    private void downRes(String url, String fileName, String path, int type) {

        File file = new File(path + "/" + fileName);
        if (file.exists()) {
        } else {
            startService(new Intent(LoadingActivity.this, DownSplashResService.class).putExtra("fileUrl", url).putExtra("fileName", fileName).putExtra("fileType", type));
        }
    }

    public void startAfterDelay() {


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000l);

    }
}
