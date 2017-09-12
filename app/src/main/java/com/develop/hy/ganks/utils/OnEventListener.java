package com.develop.hy.ganks.utils;

/**
 * Created by HY on 2017/4/12.
 */

public interface OnEventListener<T> {
    void Success(T response);
    void onFail(String errCode, String errMsg);
}
