package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.utils.AlipayZeroSdk;
import com.develop.hy.ganks.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.us_toolbar)
    Toolbar usToolbar;
    @BindView(R.id.encourage)
    TextView encourage;
    @BindView(R.id.mycsdn)
    TextView mycsdn;
    @BindView(R.id.mygithub)
    TextView mygithub;
    @BindView(R.id.suggest)
    TextView suggest;
    @BindView(R.id.bt_upgrade)
    Button bt_upgrade;
    @BindView(R.id.content_root)
    CoordinatorLayout cor_root;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(usToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            }
        });

        mygithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","https://github.com/hiliving"));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            }
        });
        mycsdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","http://blog.csdn.net/huang_yong_"));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            }
        });
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        encourage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlipayZeroSdk.hasInstalledAlipayClient(AboutUsActivity.this)) {
                    AlipayZeroSdk.startAlipayClient(AboutUsActivity.this, "FKX05183IOKBWDUKV9OF03");
                } else {
                    Snackbar.make(cor_root, "谢谢，您没有安装支付宝客户端", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        bt_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("当前已是最新版本");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
    }
}
