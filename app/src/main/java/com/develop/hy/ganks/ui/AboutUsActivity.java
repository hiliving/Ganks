package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.develop.hy.ganks.R;
import com.develop.hy.ganks.utils.AlipayZeroSdk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends AppCompatActivity {

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
    @BindView(R.id.content_root)
    CoordinatorLayout cor_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(usToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mygithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","https://github.com/hiliving"));
            }
        });
        mycsdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","http://blog.csdn.net/huang_yong_"));
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
    }
}
