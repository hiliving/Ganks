package com.develop.hy.ganks.ui;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;

/**
 * Created by Helloworld on 2017/9/17.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {

        return R.layout.setting_layout;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
    }
}
