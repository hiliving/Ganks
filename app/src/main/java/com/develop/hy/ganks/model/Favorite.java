package com.develop.hy.ganks.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Helloworld on 2017/9/19.
 */

public class Favorite extends BmobObject implements Serializable{
    private User userId;
    private String time;
    private String title;
    private String url;
    private String author;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
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
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
