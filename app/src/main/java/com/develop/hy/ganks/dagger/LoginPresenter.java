package com.develop.hy.ganks.dagger;

import android.content.Intent;

import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.ui.LoginActivity;
import com.develop.hy.ganks.ui.UserCenter;
import com.develop.hy.ganks.utils.ToastUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Helloworld on 2017/9/12.
 */

public class LoginPresenter {
    private LoginActivity loginActivity;

    public LoginPresenter(LoginActivity mainActivity) {
        this.loginActivity = mainActivity;
    }

    public void login(String username, String pwd){
        User p3 = new User();
        p3.setUsername(username);
        p3.setPassword(pwd);
        p3.login(loginActivity, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showShortToast("登录成功");
                loginActivity.startActivity(new Intent(loginActivity,UserCenter.class));
                loginActivity.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showShortToast("登录失败");
            }
        });
    }
    public void regist(String username,String pwd,String email){
        User p2 = new User();
        p2.setUsername(username);
        p2.setPassword(pwd);
        p2.setEmail(email);
        p2.signUp(loginActivity, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showShortToast("注册成功");
                if (listener!=null){
                    listener.OnFinish();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showShortToast("注册失败"+s);
                if (listener!=null){
                    listener.OnFail(s);
                }
            }
        });
    }
    private OnRegistListener listener;
    public void SetOnRegistListener(OnRegistListener listener){
        this.listener = listener;
    }
    public interface OnRegistListener{
        void OnFinish();
        void OnFail(String s);
    }
}
