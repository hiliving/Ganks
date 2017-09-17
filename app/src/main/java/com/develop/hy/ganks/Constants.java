package com.develop.hy.ganks;


import java.util.ArrayList;

/**
 * Created by HY on 2017/9/12.
 */

public class Constants {
    //SecretUtil
    public static String NOSUCHALGORITHM = "不支持此种加密方式";
    public static String UNSUPPENCODING = "不支持的编码格式";

    //Cache
    public static String FLUSH_ERRO = "DiskLruCache flush 失败！";
    public static long CACHE_MAXSIZE = 10 * 1024 * 1024; //10MB的缓存大小
    public static String GANK_KEY = "zhihu_latest_news";//缓存干货最近一页的内容
    public static String GANKCACHE = "zhihuCache";//干货的缓存文件夹名

    //SMS Activity
    public static String SMS_BROADCAST_FILTER = "gank.mvpdemo.recevieSMS";

    public static final int PAGE_SIZE = 20;

    public static final String BOMB_APPID = "74484ee79ef10cb552ab13235abbcb70";

    public static ArrayList<String> searchHistory=new ArrayList<>();//保存最近搜索记录
}
