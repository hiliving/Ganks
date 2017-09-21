package com.develop.hy.ganks.ui;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.develop.hy.ganks.utils.SPUtils;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownSplashResService extends Service {

    private static final String DEFAULT_NAME = "unfinish";
    public static String DOWNLOAD_PATH;
    public static final String TAG = "download";
    private String downUrl = "";//下载链接
    private int length;//文件长度
    private String fileName = null;//文件名
    private static final int MSG_INIT = 0;
    private static final int URL_ERROR = 1;
    private static final int NET_ERROR = 2;
    private static final int DOWNLOAD_SUCCESS = 3;
    private String name;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    length = (Integer) msg.obj;
                    new DownloadThread(downUrl, length).start();
                    break;
                case DOWNLOAD_SUCCESS:
                    break;
                case URL_ERROR:
                    break;
                case NET_ERROR:
                    break;
            }
        }

        ;
    };
    private int type;
    private SPUtils sp;


    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * 获取可存储的路径
     *
     * @param context
     */
    public static String getDownPath(Context context) {
        File f = context.getCacheDir();
        if (f != null) {
            return f.getPath() + "/splash";
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sp = new SPUtils(DownSplashResService.this, "splash");
        if (intent != null) {
            DOWNLOAD_PATH = getDownPath(this);
            downUrl = intent.getStringExtra("fileUrl");
            name = intent.getStringExtra("fileName");
            type = intent.getIntExtra("fileType", 1000);
            if (downUrl != null && !TextUtils.isEmpty(downUrl)) {
                new InitThread(downUrl, name).start();
            } else {
                mHandler.sendEmptyMessage(URL_ERROR);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化子线程
     *
     * @author dong
     */
    class InitThread extends Thread {
        String url = "";
        String name = "";

        public InitThread(String url, String name) {
            this.url = url;
            this.name = name;
        }

        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(this.url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;
                if (conn.getResponseCode() == HttpStatus.SC_OK || conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                    //获得文件长度
                    length = conn.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                fileName = name;
                //两种格式，或者为视频，或者为图片，都在此下载
                File file = new File(dir, DEFAULT_NAME);
                raf = new RandomAccessFile(file, "rwd");
                //设置文件长度
                raf.setLength(length);
                mHandler.obtainMessage(MSG_INIT, length).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(URL_ERROR);
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载线程
     *
     * @author dong
     */
    class DownloadThread extends Thread {
        String url;
        int length;

        public DownloadThread(String url, int length) {
            this.url = url;
            this.length = length;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {

                URL url = new URL(this.url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = 0;
                conn.setRequestProperty("Range", "bytes=" + 0 + "-" + length);
                //设置文件写入位置
                File file = new File(DOWNLOAD_PATH, DEFAULT_NAME);
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                long mFinished = 0;
                //开始下载
                if (conn.getResponseCode() == HttpStatus.SC_OK || conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                    //读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long speed = 0;
                    long time = System.currentTimeMillis();
                    while ((len = input.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, len);
                        mFinished += len;
                        speed += len;
                         Log.d("下载服务:","下载中"+mFinished);
                    }
                    mHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                    //下载完成，删除旧的文件
                    if (type == LoadingActivity.MODE_MOVIE) {
                        File f = new File(DOWNLOAD_PATH, sp.getString("mName", "def"));
                        if (f.exists()) {
                            f.delete();
                              Log.i(TAG, "旧的视频已删除，删除的文件名为"+sp.getString("mName","def"));
                        }
                        sp.putString("mName", fileName);
                    } else {
                        File f = new File(DOWNLOAD_PATH, sp.getString("picName", "def"));
                        if (f.exists()) {
                            f.delete();
                             Log.i(TAG, "旧的图片已删除。删除的文件名为"+sp.getString("picName","def"));
                        }
                        sp.putString("picName", fileName);
                    }
                     Log.i(TAG, "下载完成了。。。");
                    file.renameTo(new File(DOWNLOAD_PATH, fileName));
                } else {
                    mHandler.sendEmptyMessage(NET_ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

