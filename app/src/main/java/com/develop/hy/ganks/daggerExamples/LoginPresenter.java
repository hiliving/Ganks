package com.develop.hy.ganks.daggerExamples;

import android.content.Intent;
import android.widget.Toast;

import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.utils.ToastUtils;

/**
 * Created by Helloworld on 2017/9/12.
 */

public class LoginPresenter {
    private MainActivity mainActivity;

    public LoginPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void login(String username, String pwd){
        //Toast.makeText(mainActivity, "登录了", Toast.LENGTH_SHORT).show();
       // mainActivity.startActivity(new Intent(mainActivity,MainPage.class));
    }
}
