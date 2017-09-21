package com.develop.hy.ganks.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.MainActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.utils.SPUtils;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL;


/**
 * 此页面用于执行具体加载类型，图片or视频
 * Created by HY on 2017/5/8.
 */

public class SplashActivity extends BaseActivity implements ExoPlayer.EventListener {
    private static final String TAG = "SplashActivity";
    private static final int TOMAINPAGE = 20;
    private static final int TIMEUPDATE = 2;
    private String cachePath;
    private SPUtils sp;
    private Context mContext;
    private SimpleExoPlayerView playerView;
    private TrackSelector trackSelector;
    private SimpleExoPlayer mPlayer;
    private LoadControl loadControl;
    private int timeCount = 60;
    private int count = 1;
    private TextView tv_time_delay;
    private Timer timer;
    private TimerTask task;
    private DonutProgress donutProgress;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case TOMAINPAGE:
                    tryJumpToMain();
                    break;

                case TIMEUPDATE:
                    tv_time_delay.setText((timeCount - count) / 10 + "S");
                    if (count <= 50) {
                        donutProgress.setProgress(count++);
                    } else {
                        mHandler.removeMessages(TIMEUPDATE);
                        timer.cancel();
                        task.cancel();
                    }
                    break;
            }
        }
    };
    private Uri mUri;

    @Override
    protected void onPreCreate() {
        super.onPreCreate();
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }

    @Override
    protected void initView() {
        mContext = this;
        initTimer();
        initSplashRes();
    }

    /**
     * 初始化计时器，倒计时用
     */
    private void initTimer() {
        tv_time_delay = (TextView) findViewById(R.id.time_delay);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress.setMax(50);
        donutProgress.setStartingDegree(270);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(TIMEUPDATE);
            }
        };
    }
    @Override
    protected int getLayoutId() {
        return R.layout.splash_layout;
    }

    private void initSplashRes() {
        sp = new SPUtils(SplashActivity.this, "splash");
        cachePath = this.getCacheDir().getPath() + "/splash";
        showTimeDelay();
        if (getIntent().getIntExtra("type", 2000) == 1000) {
            startImage();
        } else {
            startPlayer();
        }
    }
    private void startImage() {

        ImageView bg = (ImageView) findViewById(R.id.splash_bg);
        File file = new File(cachePath, sp.getString("picName"));
        if (file.exists() && file.isFile()) {
            try {
                FileInputStream is = new FileInputStream(cachePath + "/" + sp.getString("picName"));
                bg.setImageBitmap(BitmapFactory.decodeStream(is));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            startPlayer();
        }
        mHandler.sendEmptyMessageDelayed(TOMAINPAGE, 5000);
    }
    private void showTimeDelay() {
        timer.schedule(task, 0, 100);
    }
    private void tryJumpToMain() {
        mHandler.removeMessages(TOMAINPAGE);
        releasePlayer();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void releasePlayer() {

        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            trackSelector = null;
        }
    }

    /**
     * 播放启动视频
     */
    private void startPlayer() {
        // 0.  set player view
        playerView = (SimpleExoPlayerView) findViewById(R.id.playerView);
        playerView.setUseController(false);
        playerView.getKeepScreenOn();
        playerView.setResizeMode(RESIZE_MODE_FILL);

        // 1. Create a default TrackSelector
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(new DefaultBandwidthMeter());
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        loadControl = new DefaultLoadControl();

        // 3. Create the mPlayer
        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        mPlayer.addListener(this);

        // 4. set player
        playerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(true);

        // 5. prepare to play
        File file = new File(cachePath, sp.getString("mName"));
        if (file.isFile() && file.exists()) {
            mUri = Uri.fromFile(file);
        } else {
            mUri = Uri.parse("file:///android_asset/splash.mp4");
        }
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, "UserAgent");
        MediaSource videoSource = new ExtractorMediaSource(mUri, dataSourceFactory, extractorsFactory, null, null);

        // 6. ready to play
        mPlayer.prepare(videoSource);

        mHandler.sendEmptyMessageDelayed(TOMAINPAGE, 5000);//5秒后停止视频进主页
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {

            case ExoPlayer.STATE_ENDED:
                Log.d(TAG, "player state -> STATE_ENDED");
                mPlayer.stop();
                tryJumpToMain();
                break;

            case ExoPlayer.STATE_IDLE:
                Log.d(TAG, "player state -> STATE_IDLE");
                break;

            case ExoPlayer.STATE_READY:
                Log.d(TAG, "player state -> STATE_READY");
                break;

            case ExoPlayer.STATE_BUFFERING:
                Log.d(TAG, "player state -> STATE_BUFFERING");
                break;
            default:

                break;
        }
    }
    @Override
    public void onBackPressed() {
        tryJumpToMain();
    }
    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
