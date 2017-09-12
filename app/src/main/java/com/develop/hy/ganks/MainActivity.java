package com.develop.hy.ganks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.develop.hy.ganks.daggerExamples.DaggerMainActivityComponent;
import com.develop.hy.ganks.daggerExamples.LoginPresenter;
import com.develop.hy.ganks.daggerExamples.MainActivityModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainActivityComponent component = (DaggerMainActivityComponent) DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build();
        component.in(this);

        presenter.login("name","pwd");
    }
}
