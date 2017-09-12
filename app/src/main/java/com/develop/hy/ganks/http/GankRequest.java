package com.develop.hy.ganks.http;

import android.os.Handler;

import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.utils.OnEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by HY on 2017/4/12.
 */

public class GankRequest {

    public void getContent(final String url , final OnEventListener<String> eventListener){
        final Handler handler = new Handler();
        new Thread(){
            @Override
            public void run() {
                try {
                    String result = HttpServiceManager.httpGet(url);
                    Gson gson = new Gson();
                    GankBean daily = gson.fromJson(result,GankBean.class);
                    final String stories= daily.getDesc();
                    if (stories!=null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                eventListener.Success(stories);
                            }
                        });
                    }else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                eventListener.onFail("-100","获取干货失败");
                            }
                        });
                    }
                }catch (final Exception e){
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            eventListener.onFail("-100","获取干货失败");
                        }
                    });
                }
            }
        }.start();
    }

    public void getStoryDataByRetrofit(final OnEventListener<ArrayList<GankBean>> eventListener){
        ApiManager apiManager = ApiManager.getInstance();
    }

}
