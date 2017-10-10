package com.develop.hy.ganks.presenter;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.develop.hy.ganks.App;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.http.ApiService;
import com.develop.hy.ganks.http.UrlConfig;
import com.develop.hy.ganks.model.ConfigInfo;
import com.develop.hy.ganks.model.VersionBean;
import com.develop.hy.ganks.presenter.CommenInterface.ILoadingView;
import com.develop.hy.ganks.ui.DownSplashResService;
import com.develop.hy.ganks.ui.view.DownloadService;
import com.develop.hy.ganks.ui.view.FullScreenDialog;
import com.develop.hy.ganks.utils.ToastUtils;

import java.io.File;
import java.io.Serializable;

import me.xiaopan.android.net.NetworkUtils;
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
    public LoadingPresenter(T context, ILoadingView iView) {
        super((Context) context, iView);
        this.context = (Context) context;
    }

    @Override
    public void release() {
        unSubcription();
    }
    //获取数据
    public void getUpdate(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UrlConfig.baseConfig)
                .build();

        final ApiService mApi = retrofit.create(ApiService.class);
        Call<VersionBean> request = mApi.getUpdate();
        //启动即发请求
        request.enqueue(new Callback<VersionBean>() {
            @Override
            public void onResponse(Call<VersionBean> call, Response<VersionBean> response) {

                VersionBean mInfo = response.body();
                if (mInfo.getCode()==0){
                    if (mInfo.getData().getUpdateType()==1) {
                        showUpdate(mInfo.getData());
                    } else if (mInfo.getData().getUpdateType()==2){
                        iView.showMustUpdate();
                    }else {
                        iView.showNoUpdate();
                    }
                }else {
                    iView.showErrorData();
                }
            }
            @Override
            public void onFailure(Call<VersionBean> call, Throwable t) {

            }
        });
    }

    private void showUpdate(final VersionBean.DataBean data) {
        final Dialog dialog = FullScreenDialog.getInstance(context,R.layout.update_dialog);
        dialog.setCancelable(true);
        dialog.show();
        final Button confirm = (Button) dialog.findViewById(R.id.confirm);
        Button nextTime = (Button) dialog.findViewById(R.id.nexttiem);
        TextView updateContent = (TextView) dialog.findViewById(R.id.update_content);
        updateContent.setText(data.getDescription());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始下载服务
                try {
                    if (NetworkUtils.getWifiState(App.getContext())== WifiManager.WIFI_STATE_DISABLED){
                       AlertDialog alertDialog = new AlertDialog.Builder(context,R.style.Dialog_Alert)
                               .setTitle("")
                               .setMessage("当前是非WIFI网络，确定继续下载？")
                               .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogs, int which) {
                                       Intent intent = new Intent(context,DownloadService.class);
                                       intent.putExtra("url", data.getUrl());
                                       context.startService(intent);
                                       //显示下载进度
                                   }
                               })
                               .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogs, int which) {
                                   }
                               })
                               .setCancelable(false)
                               .show();
                    }else {
                        Intent intent = new Intent(context,DownloadService.class);
                        intent.putExtra("url", data.getUrl());
                        context.startService(intent);
                        //显示下载进度
                        iView.showProgress();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        nextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iView.showErrorData();
            }
        });
    }

    public void getConfig(final Context context, final Intent intent) {

        iView.showNoUpdate();//临时代码
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
