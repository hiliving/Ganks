package com.develop.hy.ganks.http.mvc;


/**
 * Created by dongjunkun on 2016/7/13.
 */
public interface HttpListener {
    void onSuccess(Object result);

    void onFailure(int errorType, String message);
}
