package com.develop.hy.ganks.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit请求管理类
 * Created by HY on 2017/4/12.
 */

public class ApiManager {
    private ApiService mDailyApi;
    private static ApiManager sApiManager;
    public static ApiManager getInstance(){
        if (sApiManager==null){
            synchronized (ApiManager.class){
                if (sApiManager==null){
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }
    /**
     * 封装配置知乎API
     */
    public ApiService getDataService(){
        OkHttpClient client = new OkHttpClient.Builder()
                //添加应用拦截器
                .addInterceptor(new HttpInterceptor())
                .build();
        if (mDailyApi==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConfig.baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mDailyApi = retrofit.create(ApiService.class);
        }
        return mDailyApi;
    }
}
