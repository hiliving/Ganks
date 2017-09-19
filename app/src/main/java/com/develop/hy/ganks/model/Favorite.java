package com.develop.hy.ganks.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Helloworld on 2017/9/19.
 */

public class Favorite extends BmobObject {
    private String userId;
    private String time;
    private String title;
    private String Url;
    private String author;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
