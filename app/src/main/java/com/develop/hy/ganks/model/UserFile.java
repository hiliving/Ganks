package com.develop.hy.ganks.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by HY on 2017/9/20.
 */

public class UserFile extends BmobObject{
    private BmobFile bg_img;
    private BmobFile head_icon;
    private User userId;
    private String bgimg;
    private String headImg;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBgimg() {
        return bgimg;
    }

    public void setBgimg(String bgimg) {
        this.bgimg = bgimg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public UserFile() {
    }

    public UserFile(BmobFile head_icon, User userId) {
        this.head_icon = head_icon;
        this.userId = userId;
    }

    public BmobFile getBg_img() {
        return bg_img;
    }

    public void setBg_img(BmobFile bg_img) {
        this.bg_img = bg_img;
    }

    public BmobFile getHead_icon() {
        return head_icon;
    }

    public void setHead_icon(BmobFile head_icon) {
        this.head_icon = head_icon;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
