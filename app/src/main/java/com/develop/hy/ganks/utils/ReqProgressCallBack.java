package com.develop.hy.ganks.utils;

import java.io.File;

/**
 * Created by Helloworld on 2017/9/23.
 */

public interface ReqProgressCallBack<T> {
    /**
     * 响应进度更新
     */
    void onProgress(long total, long current);

    /**
     * 下载失败
     */
    void onDownLoadFail();

    void onSuccess(File file);
}
