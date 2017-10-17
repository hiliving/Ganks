package com.develop.hy.ganks.presenter;


import android.content.Context;
import android.content.Intent;
import com.develop.hy.ganks.http.ApiService;
import com.develop.hy.ganks.http.UrlConfig;
import com.develop.hy.ganks.model.ConfigInfo;
import com.develop.hy.ganks.presenter.CommenInterface.ILoadingView;
import com.develop.hy.ganks.ui.DownSplashResService;
import java.io.File;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.develop.hy.ganks.ui.LoadingActivity.MODE_MOVIE;
import static com.develop.hy.ganks.ui.LoadingActivity.MODE_PICTURE;

/**
 * Created by HY on 2017/9/12.
 */

public class LoadingPresenter<T> extends BasePresenter<ILoadingView> implements Serializable{
    private Context context;
    private UpdatePresenter updatePresenter;

    public LoadingPresenter(T context, ILoadingView iView) {
        super((Context) context, iView);
        this.context = (Context) context;
    }
    @Override
    public void release() {
        unSubcription();
        updatePresenter= null;
    }
    //获取数据
    public void getUpdate(){
        updatePresenter = new UpdatePresenter(context,iView);
        updatePresenter.getUpdate();
    }

    public void getConfig(final Context context, final Intent intent) {

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
                String filePath =context.getCacheDir().getPath() + "/splash";//这里用的内置sd卡
                if (mInfo.getData().getFileType() == 2) {
                    //启动为视频
                    // downRes(fileUrl, fileName, filePath, MODE_MOVIE);
                    intent.putExtra("type", MODE_MOVIE);
                } else {
                    //启动为图片
                    downRes(fileUrl, fileName, filePath, MODE_PICTURE);
                    intent.putExtra("type", MODE_PICTURE);
                }
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
            context.startService(new Intent(context, DownSplashResService.class).putExtra("fileUrl", url).putExtra("fileName", fileName).putExtra("fileType", type));
        }
    }

}
