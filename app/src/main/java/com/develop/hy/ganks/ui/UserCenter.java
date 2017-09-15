package com.develop.hy.ganks.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by HY on 2017/9/14.
 */

public class UserCenter extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.bt_logout)
    Button logout;
    @BindView(R.id.bt_collect)
    Button collect;
    @BindView(R.id.bt_clear_cache)
    Button clear;
    @BindView(R.id.tv_username)
    TextView username;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initEvent();
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        username.setText("昵称:"+bmobUser.getUsername());
    }

    private void initEvent() {
        logout.setOnClickListener(this);
        collect.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.usercenter_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_logout:
                BmobUser.logOut(this);
                finish();
                break;
            case R.id.bt_collect:
                ToastUtils.showShortToast("功能正在开发中");
                break;
            case R.id.bt_clear_cache:
                ToastUtils.showShortToast("功能正在开发中");
                break;
        }
    }
}
