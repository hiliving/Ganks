package com.develop.hy.ganks.http;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.develop.hy.ganks.http.ApiService;
import com.develop.hy.ganks.http.HttpInterceptor;
import com.develop.hy.ganks.http.UrlConfig;
import com.develop.hy.ganks.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HY on 2017/9/13.
 */

public class ApiManager {
    private static ApiService api;
    protected static final Object monitor = new Object();
    private static Retrofit retrofit;
    private static OkHttpClient client;

    private ApiManager() {
    }
    static {
        //添加应用拦截器
        client = new OkHttpClient.Builder()
                //添加应用拦截器
                .addInterceptor(new HttpInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(UrlConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }
    public static ApiService getGankRetrofitInstance(Context context,String BaseUrl){

        if (api==null){
            synchronized (monitor){
                api = retrofit.create(ApiService.class);
            }
        }
        return api;
    }
}
