package com.develop.hy.ganks.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;

import com.develop.hy.ganks.App;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.http.ApiService;
import com.develop.hy.ganks.http.UrlConfig;
import com.develop.hy.ganks.model.VersionBean;
import com.develop.hy.ganks.presenter.CommenInterface.ILoadingView;
import com.develop.hy.ganks.ui.view.DownloadService;
import com.develop.hy.ganks.utils.NetworkUtils;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HY on 2017/10/17.
 */

public class UpdatePresenter<T>{
    private Context context;
    private ILoadingView ivew;

    public UpdatePresenter(Context context, T iView) {
        this.context =context;
        this.ivew = (ILoadingView) iView;
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
                        ivew.showMustUpdate();
                    }else {
                        ivew.showNoUpdate();
                    }
                }else {

                }
            }
            @Override
            public void onFailure(Call<VersionBean> call, Throwable t) {
                ivew.showErrorData();
            }
        });
    }
    private void showUpdate(final VersionBean.DataBean data) {
        showDialog(data);
    }

    private void showDialog(final VersionBean.DataBean data) {
        //实例化建造者
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置警告对话框的标题
        builder.setTitle("发现新版本:v"+data.getVersion());
        //设置警告显示的图片
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置警告对话框的提示信息
        builder.setMessage(data.getDescription());
        //设置”正面”按钮，及点击事件
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                //开始下载服务
                try {
                    if (NetworkUtils.getWifiState(App.getContext())== WifiManager.WIFI_STATE_DISABLED){
                        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.Dialog_Alert)
                                .setTitle("")
                                .setMessage("当前是非WIFI网络，确定继续下载？")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogs, int which) {
                                        Intent intent = new Intent(context,DownloadService.class);
                                        intent.putExtra("url", data.getUrl());
                                        context.startService(intent);
                                        dialog.dismiss();
                                        ivew.showProgressBar();
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
                        dialog.dismiss();

                        ivew.showProgressBar();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        //设置“反面”按钮，及点击事件
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ivew.showErrorData();
            }
        });
        //显示对话框
        builder.show();
    }
}
