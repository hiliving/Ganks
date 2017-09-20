package com.develop.hy.ganks.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.develop.hy.ganks.R;

/**
 * Created by Helloworld on 2017/9/20.
 */

public class TipTitleView extends RelativeLayout {
    public TipTitleView(Context context) {
        this(context,null);
    }

    public TipTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TipTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.tip_title_view,this);
        addView(view);
    }
}
