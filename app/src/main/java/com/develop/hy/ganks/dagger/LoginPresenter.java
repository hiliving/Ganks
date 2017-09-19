package com.develop.hy.ganks.dagger;

import android.app.ListActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.ui.LoginActivity;
import com.develop.hy.ganks.ui.UserCenter;
import com.develop.hy.ganks.utils.ToastUtils;

import cn.bmob.v3.listener.SaveListener;
import top.wefor.circularanim.CircularAnim;

/**
 * Created by Helloworld on 2017/9/12.
 */

public class LoginPresenter {
    private LoginActivity loginActivity;

    public LoginPresenter(LoginActivity mainActivity) {
        this.loginActivity = mainActivity;
    }

    public void login(String username, String pwd, final Button login, final ProgressBar progressbar){
        User p3 = new User();
        p3.setUsername(username);
        p3.setPassword(pwd);
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(pwd)){
            ToastUtils.showShortToast("用户名或密码不能为空");
            return;
        }
        p3.login(loginActivity, new SaveListener() {
            @Override
            public void onSuccess() {
                progressbar.setVisibility(View.VISIBLE);
                startToUserCenter(login,progressbar);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showShortToast("登录失败");
                progressbar.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
            }
        });
    }

   public void startToUserCenter(Button login, final ProgressBar progressbar){

       progressbar.postDelayed(new Runnable() {
           @Override
           public void run() {
               CircularAnim.fullActivity(loginActivity, progressbar)
                       .go(new CircularAnim.OnAnimationEndListener() {
                           @Override
                           public void onAnimationEnd() {
                               loginActivity.startActivity(new Intent(loginActivity, UserCenter.class));
                               loginActivity.finish();
                           }
                       });
           }
       }, 2000);


    }
    public void regist(String username, String pwd, String email, final ProgressBar progressbar){
        progressbar.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(pwd)){
            ToastUtils.showShortToast("用户名或密码不能为空");
            return;
        }

        User p2 = new User();
        p2.setUsername(username);
        p2.setPassword(pwd);
        p2.setEmail(email);
        p2.signUp(loginActivity, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showShortToast("注册成功");
                progressbar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CircularAnim.fullActivity(loginActivity, progressbar)
                                .go(new CircularAnim.OnAnimationEndListener() {
                                    @Override
                                    public void onAnimationEnd() {
                                        if (listener!=null){
                                            listener.OnFinish();
                                        }
                                    }
                                });
                    }
                }, 2000);
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
