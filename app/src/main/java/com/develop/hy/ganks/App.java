package com.develop.hy.ganks;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.develop.hy.ganks.utils.Utils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

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

        Logger.init("hhh")
                .methodOffset(2)
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
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
