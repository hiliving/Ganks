package com.develop.hy.ganks.dagger;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.model.UserFile;
import com.develop.hy.ganks.ui.LoginActivity;
import com.develop.hy.ganks.ui.UserCenterActivity;
import com.develop.hy.ganks.utils.ToastUtils;

import java.util.logging.Logger;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
       p3.login(new SaveListener<User>() {
           @Override
           public void done(User user, BmobException e) {
               if (user!=null){
                   startToUserCenter(login,progressbar);
               }else {
                   ToastUtils.showShortToast("登录失败"+e.getMessage());
                   progressbar.setVisibility(View.INVISIBLE);
                   login.setVisibility(View.VISIBLE);
               }
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
                               loginActivity.startActivity(new Intent(loginActivity, UserCenterActivity.class));
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

        p2.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user!=null){
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

                                                createUserInfo();
                                            }
                                        }
                                    });
                        }
                    }, 2000);
                }
            }
        });



    }

    private void createUserInfo() {
        Log.d("插入数据AAAAAAAAAA","正在插入数据");
        final User user = BmobUser.getCurrentUser(User.class);
        final UserFile userFile = new UserFile();
        userFile.setUsername(user.getUsername());
        userFile.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Log.d("插入数据",userFile.getObjectId());
                updateUserInfo(user,userFile);
            }
        });
    }

    private void updateUserInfo(User user, UserFile userFile) {
        //然后在User表中更新UserInfo字段的值为UserFile的ObjectId
        user.setUserInfoId(userFile.getObjectId());
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.d("插入数据","User表更新成功");
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
