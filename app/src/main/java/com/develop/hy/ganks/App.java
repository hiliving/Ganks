package com.develop.hy.ganks;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.develop.hy.ganks.utils.Utils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import top.wefor.circularanim.CircularAnim;

/**
 * Created by HY on 2017/9/12.
 */

public class App extends Application {
    private static App sMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);

        sMyApplication = this;

        CircularAnim.init(700, 500, R.color.colorPrimary);

        Logger.init("hhh")
                .methodOffset(2)
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        //初始化bomb
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId(Constants.BOMB_APPID)
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);

    }
    /**
     * 获取全局 context
     * @return 全局context
     */
    public static Context getContext() {
        return sMyApplication.getApplicationContext();
    }

    /**
     * 获取应用的版本号
     * @return 应用版本号
     */
    public static int getAppVersion() {
        Context context = getContext();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
