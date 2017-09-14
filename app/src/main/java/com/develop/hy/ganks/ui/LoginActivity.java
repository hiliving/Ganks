package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Helloworld on 2017/9/14.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.regist)
    Button regist;

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        login.setOnClickListener(this);
        regist.setOnClickListener(this);
        initEvent();
    }

    private void initEvent() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>16){
                    username.setError("请输入少于16个字符");
                }
                Log.d("DDDDD",s.toString());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_layout;
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
                startActivity(new Intent(LoginActivity.this,UserCenter.class));
                finish();
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
