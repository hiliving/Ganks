package com.develop.hy.ganks.dagger;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.ui.LoginActivity;
import com.develop.hy.ganks.ui.UserCenter;

import cn.bmob.v3.BmobUser;

/**
 * Created by Helloworld on 2017/9/15.
 */

public class MainPresenter {
    private MainActivity activity;

    public MainPresenter(MainActivity activity) {
        this.activity = activity;
    }

    public void initUserInfo(final MainActivity activity, TextView userid, ImageView userIcon, RelativeLayout rl) {
        BmobUser bmobUser = BmobUser.getCurrentUser(activity);
        if (bmobUser!=null){
            userid.setText(bmobUser.getUsername());
            userIcon.setBackgroundResource(R.mipmap.ic_launcher_round);
        }
        if (bmobUser!=null){
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity,UserCenter.class));
                }
            });
        }else {
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity,LoginActivity.class));
                }
            });
        }
    }
}
