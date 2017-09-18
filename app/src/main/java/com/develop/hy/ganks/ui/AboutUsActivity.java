package com.develop.hy.ganks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.hy.ganks.R;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

public class AboutUsActivity extends AppCompatActivity {

    private TextView textView;
    private Button mygithub;
    private AgentWeb mAgentWeb;
    private CoordinatorLayout cor_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        textView = (TextView) findViewById(R.id.title_aboutus);
        cor_root = (CoordinatorLayout) findViewById(R.id.content_root);
        mygithub = (Button) findViewById(R.id.mygithub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","https://github.com/hiliving"));
            }
        });
        mygithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(AboutUsActivity.this,WebViewActivity.class).putExtra("URL","https://github.com/hiliving"));
            }
        });
    }
}
