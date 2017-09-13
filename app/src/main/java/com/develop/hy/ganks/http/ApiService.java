package com.develop.hy.ganks.http;

import com.develop.hy.ganks.model.GankBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by HY on 2017/4/12.
 */

public interface ApiService {


//    @GET("data/{category}/{pageCount}/{page}")
//    Observable<GankData> getGank(@Path("category") String category, @Path("pageCount") int pageCount, @Path("page") int page);
    @GET("data/{category}/{pageCount}/{page}")
    Observable<GankBean> getGank(@Path("category") String category, @Path("pageCount") int pageCount, @Path("page") int page);
}
