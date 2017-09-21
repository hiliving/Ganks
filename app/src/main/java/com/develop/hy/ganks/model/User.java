package com.develop.hy.ganks.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by Helloworld on 2017/9/14.
 */

public class User extends BmobUser {
    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    private String userInfoId;
}
