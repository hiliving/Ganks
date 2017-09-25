package com.develop.hy.ganks.http;

import com.develop.hy.ganks.model.ConfigInfo;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.model.NewsInfo;
import com.develop.hy.ganks.model.VersionBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *  随机数据： http://gank.io/api/random/data/分类/个数
 * Created by HY on 2017/4/12.
 */

public interface ApiService {


    @GET("data/{category}/{pageCount}/{page}")
    Observable<GankBean> getGank(@Path("category") String category, @Path("pageCount") int pageCount, @Path("page") int page);

    @GET("search/query/{queryText}/category/all/count/{count}/page/{page}")
    Observable<GankBean> queryGank(@Path("queryText") String queryText, @Path("count") int count, @Path("page") int page);


    @GET("random/data/{category}/{count}")
    Observable<GankBean> queryRandom(@Path("category") String category, @Path("count") int count);
    @GET("gank/stmode.json")
    Call<ConfigInfo> getType();

    @GET("gank/update.json")
    Call<VersionBean> getUpdate();
    //startup/?key=APIKEY&num=10
    @GET("startup/")
    Call<NewsInfo> queryNews(@Query("key") String appkey, @Query("num") String num);
}
