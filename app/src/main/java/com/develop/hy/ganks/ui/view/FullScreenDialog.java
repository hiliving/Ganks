package com.develop.hy.ganks.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.develop.hy.ganks.R;

/**
 * 全屏dialog的封装类
 * Created by HY on 2017/8/16.
 */

public class FullScreenDialog {
    private static Dialog dialog;

    public static Dialog getInstance(Context context, int layoutId) {
        dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(layoutId);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }
}

