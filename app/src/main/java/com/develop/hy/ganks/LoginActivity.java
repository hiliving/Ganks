package com.develop.hy.ganks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.utils.ToastUtils;

import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Helloworld on 2017/9/14.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button login;
    private Button regist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        login = (Button) findViewById(R.id.login);
        regist = (Button) findViewById(R.id.regist);
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        if (bmobUser!=null){
            ToastUtils.showShortToast("用户已登录");
        }else {
            ToastUtils.showShortToast("用户未登录");
        }
    }

    private void add() {
        User p2 = new User();
        p2.setUsername("lucky");
        p2.setPassword("123456");
        p2.setEmail("1195996300@qq.com");
        p2.signUp(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showShortToast("注册成功");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showShortToast("注册失败");
            }
        });
    }
    private void query(){
        User p3 = new User();
        p3.setUsername("lucky");
        p3.setPassword("123456");
        p3.login(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showShortToast("登录成功");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showShortToast("登录失败");
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                query();
                break;
            case R.id.regist:
                add();
                break;
        }
    }
}
