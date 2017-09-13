package com.develop.hy.ganks.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.andremion.floatingnavigationview.FloatingNavigationView;

/**
 * Created by HY on 2017/9/13.
 */

public class HXRecyclerView extends RecyclerView {
    private boolean isScrollingToBottom = true;
    private LoadMoreListener listener;
    private FloatingNavigationView floatingActionButton;
    public HXRecyclerView(Context context) {
        super(context);
    }

    public HXRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HXRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.listener = loadMoreListener;
    }

    public interface LoadMoreListener{
        void loadMore();
    }
    public void applyFloatingActionButton(FloatingNavigationView floatingActionButton) {
        this.floatingActionButton = floatingActionButton;
    }
    @Override
    public void onScrolled(int dx, int dy) {
        isScrollingToBottom = dy>0;
        if (floatingActionButton!=null){
            if (isScrollingToBottom){
                if (floatingActionButton.isShown()){
                    floatingActionButton.hide();
                }
            }else {
                if (!floatingActionButton.isShown()){
                    floatingActionButton.show();
                }
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
        if (state == RecyclerView.SCROLL_STATE_IDLE){
            int lastVisibleItem = manager.findLastVisibleItemPosition();
            int totalItemCount = manager.getItemCount();
            if (lastVisibleItem == (totalItemCount-1)&&isScrollingToBottom){
                if (listener!=null){
                    listener.loadMore();
                }
            }
        }
    }
}
