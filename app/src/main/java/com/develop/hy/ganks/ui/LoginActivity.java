package com.develop.hy.ganks.ui;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.dagger.component.DaggerLoginActivityComponent;
import com.develop.hy.ganks.dagger.module.LoginActivityModule;
import com.develop.hy.ganks.dagger.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @Inject
    LoginPresenter presenter;
    private String userStr;
    private String pwdStr;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        Inject();
        initEvent();
    }
    private void Inject() {
        //dagger演示代码
        DaggerLoginActivityComponent component = (DaggerLoginActivityComponent) DaggerLoginActivityComponent.builder()
                .loginActivityModule(new LoginActivityModule(this))
                .build();
        component.in(this);
    }
    private void initEvent() {
        login.setOnClickListener(this);
        regist.setOnClickListener(this);

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
                userStr = s.toString();
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>16){
                    password.setError("请输入少于16个字符");
                }
                Log.d("DDDDD",s.toString());
                pwdStr = s.toString();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                presenter.login(userStr,pwdStr);
                break;
            case R.id.regist:
                presenter.regist(userStr,pwdStr);
                break;
        }
    }
}
