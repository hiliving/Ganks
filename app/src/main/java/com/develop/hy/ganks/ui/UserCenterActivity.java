package com.develop.hy.ganks.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.GankBean;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.model.UserFile;
import com.develop.hy.ganks.presenter.CommenInterface.IFavoriteView;
import com.develop.hy.ganks.presenter.FavoritePresenter;
import com.develop.hy.ganks.ui.view.GlideImageLoader;
import com.develop.hy.ganks.utils.ToastUtils;
import com.yancy.gallerypick.adapter.PhotoAdapter;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by HY on 2017/9/14.
 */

public class UserCenterActivity extends BaseActivity implements View.OnClickListener,IFavoriteView {

    private static final String TAG = "UserCenterActivity";
    private static boolean IS_USER_BG = true;
    @BindView(R.id.bt_logout)
    Button logout;
    @BindView(R.id.bt_collect)
    TextView collect;
    @BindView(R.id.user_toolbar)
    Toolbar userToolbar;
    @BindView(R.id.bt_clear_cache)
    TextView clear;
    @BindView(R.id.tv_username)
    TextView username;
    @BindView(R.id.user_header)
    ImageView userHeader;
    @BindView(R.id.user_bg)
    ImageView user_bg;
    private FavoritePresenter presenter;
    private ArrayList<Favorite> list= new ArrayList<>();;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private List<String> path = new ArrayList<>();
    private IHandlerCallBack iHandlerCallBack;
    private GalleryConfig galleryConfig;
    private PhotoAdapter photoAdapter;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initEvent();
        setTitle("");
        setSupportActionBar(userToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        User bmobUser = BmobUser.getCurrentUser(User.class);
        username.setText(bmobUser.getUsername());
//        Glide.with(this).load(bmobUser.getHead_icon()).placeholder(R.mipmap.haveno_login).into(userHeader);
//        Glide.with(this).load(bmobUser.getHead_icon()).placeholder(R.mipmap.e).into(user_bg);
        presenter = new FavoritePresenter(this,this);
        presenter.init();
        initGallery();

        // ImageLoader 加载框架（必填）
        // 监听接口（必填）
        // provider(必填)
        // 记录已选的图片
        // 是否多选   默认：false
        // 配置是否多选的同时 配置多选数量   默认：false ， 9
        // 配置多选时 的多选数量。    默认：9
        // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
        // 配置裁剪功能的参数，   默认裁剪比例 1:1
        // 是否现实相机按钮  默认：false
        // 图片存放路径
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.yancy.gallerypickdemo.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    private void initEvent() {
        logout.setOnClickListener(this);
        collect.setOnClickListener(this);
        clear.setOnClickListener(this);
        user_bg.setOnClickListener(this);
        userHeader.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.usercenter_layout;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_logout:
                BmobUser.logOut();
                finish();
                break;
            case R.id.bt_collect:
                getFavorite();
                break;
            case R.id.bt_clear_cache:
                ToastUtils.showShortToast("功能正在开发中");
                break;
            case R.id.user_bg:
                IS_USER_BG = true;
                pushImage();
                break;
            case R.id.user_header:
                IS_USER_BG = false;
                pushImage();
                break;

        }
    }


    private void pushImage() {
        checkPermissions();
    }
    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                path.clear();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    path.add(s);
                }
                    presenter.pushImage(path);//上传头像

            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(UserCenterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                //询问
                ActivityCompat.requestPermissions(UserCenterActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSIONS_REQUEST_READ_CONTACTS);
                return;
            }else{
                //继续往下走
                ActivityCompat.requestPermissions(UserCenterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            //直接往下走
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UserCenterActivity.this);
        }



       /* if (ContextCompat.checkSelfPermission(UserCenterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserCenterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i(TAG, "拒绝过了");
                // 提示用户如果想要正常使用，要手动去设置中授权。
                Toast.makeText(UserCenterActivity.this, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(UserCenterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            Log.i(TAG, "不需要授权 ");
            // 进行正常操作
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UserCenterActivity.this);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[],int[] grantResults) {
            if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "同意授权");
                    // 进行正常操作。
                    // 进行正常操作
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UserCenterActivity.this);
                } else {
                    Log.i(TAG, "拒绝授权");
                }
            }
    }
    private void getFavorite() {
        if (list.size()==0){
            ToastUtils.showShortToast("当前还没有收藏任何内容");
            return;
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Favorite",list);
            startActivity(new Intent(UserCenterActivity.this, CommonListActivity.class)
                    .putExtra("Data",bundle));
        }
    }


    @Override
    public void initViews() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showErrorData() {

    }

    @Override
    public void showNoMoreData() {

    }

    @Override
    public void initData(List<Favorite> favorites) {
        list.clear();
        list.addAll(favorites);
    }

    @Override
    public void initUserInfo(List<UserFile> imgInfo) {
        Glide.with(this).load(imgInfo.get(0).getHeadImg()).placeholder(R.mipmap.haveno_login).into(userHeader);
        Glide.with(this).load(imgInfo.get(0).getBgimg()).placeholder(R.mipmap.e).into(user_bg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getFavorite();
        presenter.getUserBgAndHeadImg();
    }
}
