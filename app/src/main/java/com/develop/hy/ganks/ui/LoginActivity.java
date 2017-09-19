package com.develop.hy.ganks.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.a.a.V;
import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.dagger.component.DaggerLoginActivityComponent;
import com.develop.hy.ganks.dagger.module.LoginActivityModule;
import com.develop.hy.ganks.dagger.LoginPresenter;
import com.develop.hy.ganks.ui.view.WavePageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wefor.circularanim.CircularAnim;

/**
 * Created by Helloworld on 2017/9/14.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static boolean VISBLE =true ;
    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.email_inputs)
    TextInputEditText emaiStr;
    @BindView(R.id.email_input)
    TextInputLayout emailLayout;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.progressbar_regist)
    ProgressBar progressbar_regist;
    @BindView(R.id.regist)
    Button regist;
    @BindView(R.id.bt_regist)
    Button btRegist;
    @BindView(R.id.wave_view)
    WavePageView waveview;
    @BindView(R.id.anim_image)
    ImageView animImage;
    @Inject
    LoginPresenter presenter;
    private String userStr;
    private String pwdStr;
    private String emails;

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
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
        lp.gravity = Gravity.BOTTOM|Gravity.CENTER;
        waveview.setOnWaveAnimationListener(new WavePageView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0,0,0,(int)y+130);
                animImage.setLayoutParams(lp);
            }
        });

        login.setOnClickListener(this);
        regist.setOnClickListener(this);
        btRegist.setOnClickListener(this);
        presenter.SetOnRegistListener(new LoginPresenter.OnRegistListener() {
            @Override
            public void OnFinish() {
                CircularAnim.fullActivity(LoginActivity.this, progressbar_regist)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(LoginActivity.this, UserCenter.class));
                                finish();
                            }
                        });
            }

            @Override
            public void OnFail(String s) {
                progressbar_regist.setVisibility(View.INVISIBLE);
                regist.setVisibility(View.VISIBLE);
            }
        });
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
        emaiStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("DDDDD",s.toString());
                emails = s.toString();
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
                login.setVisibility(View.INVISIBLE);
               presenter.login(userStr,pwdStr,login,progressbar);
                break;
            case R.id.regist:
                toggle();
                break;
            case R.id.bt_regist:
                btRegist.setVisibility(View.INVISIBLE);
                presenter.regist(userStr,pwdStr,emails,progressbar_regist);
                break;
        }
    }

    private void toggle() {
        if (!VISBLE){
            regist.setText("立即注册");
            btRegist.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            emailLayout.setVisibility(View.INVISIBLE);
            VISBLE=true;
        }else {
            regist.setText("立即登录");
            emailLayout.setVisibility(View.VISIBLE);
            btRegist.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            VISBLE=false;
        }
    }
}
