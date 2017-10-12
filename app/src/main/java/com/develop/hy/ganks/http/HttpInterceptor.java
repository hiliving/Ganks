package com.develop.hy.ganks.http;

import android.net.wifi.WifiManager;
import android.util.Log;

import com.develop.hy.ganks.App;
import com.develop.hy.ganks.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 * Created by HY on 2017/4/12.
 */

public class HttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //打印请求链接
        String TAG_REQUEST = "request";
        Log.e(TAG_REQUEST,request.url().toString());
        Response response =  chain.proceed(request);
        return response;
    }
}
